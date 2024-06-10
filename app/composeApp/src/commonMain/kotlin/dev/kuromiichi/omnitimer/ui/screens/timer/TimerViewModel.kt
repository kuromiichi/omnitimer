package dev.kuromiichi.omnitimer.ui.screens.timer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.kuromiichi.omnitimer.data.models.Solve
import dev.kuromiichi.omnitimer.data.models.Status
import dev.kuromiichi.omnitimer.data.repositories.SettingsRepository
import dev.kuromiichi.omnitimer.data.repositories.SettingsRepositoryImpl
import dev.kuromiichi.omnitimer.data.repositories.SolvesRepository
import dev.kuromiichi.omnitimer.data.repositories.SolvesRepositoryImpl
import dev.kuromiichi.omnitimer.platform.toImageBitmap
import dev.kuromiichi.omnitimer.services.StatsService
import dev.kuromiichi.omnitimer.services.TNoodleService
import dev.kuromiichi.omnitimer.utils.getTimeStringFromMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState = _uiState.asStateFlow()

    private val settingsRepository: SettingsRepository = SettingsRepositoryImpl
    private val solvesRepository: SolvesRepository = SolvesRepositoryImpl
    private val statsService = StatsService

    private var settings: Map<String, String> = settingsRepository.getSettings()

    private var startTime: Long = 0
    private var endTime: Long = 0
    private var isPlusTwo: Boolean = false

    private var lastSolve: Solve? = null

    private var timerJob: Job? = null
    private var inspectionJob: Job? = null

    init {
        refreshScramble()
        generateStats()
    }

    fun refreshScramble() {
        val scramble = TNoodleService.getScramble(uiState.value.subcategory.category)
        _uiState.value = _uiState.value.copy(scramble = scramble)
    }

    fun generateStats() {
        val stats = mutableMapOf<String, String>()

        if (settings["best"] == "true") {
            stats["Best time:"] =
                statsService.getBest(solvesRepository.getBestSolve(uiState.value.subcategory))
        }
        if (settings["mo3"] == "true") {
            stats["mo3:"] = statsService.getMo3(
                solvesRepository.getLastNSolves(3, uiState.value.subcategory)
                    .filter { !it.isArchived }
            )
        }
        if (settings["ao5"] == "true") {
            stats["ao5:"] = statsService.getAo5(
                solvesRepository.getLastNSolves(5, uiState.value.subcategory)
                    .filter { !it.isArchived }
            )
        }
        if (settings["ao12"] == "true") {
            stats["ao12:"] = statsService.getAo12(
                solvesRepository.getLastNSolves(12, uiState.value.subcategory)
                    .filter { !it.isArchived }
            )
        }
        if (settings["ao50"] == "true") {
            stats["ao50:"] = statsService.getAo50(
                solvesRepository.getLastNSolves(50, uiState.value.subcategory)
                    .filter { !it.isArchived }
            )
        }
        if (settings["ao100"] == "true") {
            stats["ao100:"] =
                statsService.getAo100(
                    solvesRepository.getLastNSolves(100, uiState.value.subcategory)
                        .filter { !it.isArchived }
                )
        }
        _uiState.value = _uiState.value.copy(stats = stats)
    }

    fun getImage(svg: String): @Composable () -> Unit {
        return {
            Image(
                bitmap = svg.toImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    fun getTime(): String {
        return when (uiState.value.timerState) {
            TimerState.Inspection -> {
                val time = uiState.value.time / 1000 - 1
                if (isPlusTwo) "+2" else "$time"
            }

            else -> {
                getTimeStringFromMillis(
                    if (uiState.value.timerState == TimerState.Running) {
                        (System.currentTimeMillis() - startTime)
                    } else {
                        (endTime - startTime)
                    }
                )
            }
        }
    }

    fun toggleTimerState() {
        when (uiState.value.timerState) {
            TimerState.Stopped -> {
                when (settings["inspection"]) {
                    "true" -> startInspection()
                    else -> startSolve()
                }
            }

            TimerState.Inspection -> {
                startSolve()
            }

            TimerState.Running -> {
                finishSolve()
            }
        }
    }

    private fun startInspection() {
        val inspectionEnd = System.currentTimeMillis() + 17000

        _uiState.value = _uiState.value.copy(timerState = TimerState.Inspection)

        inspectionJob = viewModelScope.launch(dispatcher) {
            while (
                uiState.value.timerState == TimerState.Inspection
                && System.currentTimeMillis() < inspectionEnd
            ) {
                if (inspectionEnd - System.currentTimeMillis() < 2000) {
                    isPlusTwo = true
                }
                _uiState.value = _uiState.value
                    .copy(time = inspectionEnd - System.currentTimeMillis())
                delay(10)
            }
        }
    }

    private fun startSolve() {
        if (uiState.value.timerState == TimerState.Inspection) {
            inspectionJob?.cancel()
        }

        startTime = System.currentTimeMillis()
        _uiState.value = uiState.value.copy(timerState = TimerState.Running)

        timerJob = viewModelScope.launch(dispatcher) {
            while (uiState.value.timerState == TimerState.Running) {
                _uiState.value = _uiState.value
                    .copy(time = System.currentTimeMillis() - startTime)
                delay(10)
            }
        }
    }

    private fun finishSolve() {
        endTime = System.currentTimeMillis()
        _uiState.value = _uiState.value.copy(
            timerState = TimerState.Stopped,
            time = endTime - startTime
        )
        timerJob?.cancel()

        // TODO: save solve
        // TODO: update stats
    }

    fun changePenalty(penalty: Status) {
        _uiState.value = _uiState.value.copy(penalty = penalty)

        // TODO: update repository
    }

    fun deleteLastSolve() {
        if (lastSolve == null) return

        // TODO: delete from repository
    }

    enum class TimerState {
        Stopped, Inspection, Running
    }
}

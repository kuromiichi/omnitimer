package dev.kuromiichi.omnitimer.ui.screens.timer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.kuromiichi.omnitimer.data.models.Solve
import dev.kuromiichi.omnitimer.data.models.Status
import dev.kuromiichi.omnitimer.platform.toImageBitmap
import dev.kuromiichi.omnitimer.services.TNoodleService
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

    private var startTime: Long = 0
    private var endTime: Long = 0
    private var isRunning: Boolean = false

    private var lastSolve: Solve? = null

    private var timerJob: Job? = null

    init {
        refreshScramble()
    }

    fun refreshScramble() {
        val scramble = TNoodleService.getScramble(uiState.value.subcategory.category)
        _uiState.value = _uiState.value.copy(scramble = scramble)
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
        return getTimeStringFromMillis(
            if (isRunning) {
                (System.currentTimeMillis() - startTime)
            } else {
                (endTime - startTime)
            }
        )
    }

    private fun getTimeStringFromMillis(millis: Long): String {
        val hours = millis / (1000 * 60 * 60)
        val minutes = (millis / (1000 * 60)) % 60
        val seconds = (millis / 1000) % 60
        val hundredths = (millis / 10) % 100

        return if (hours > 0) {
            "${hours}:${minutes.toString().padStart(2, '0')}:${
                seconds.toString().padStart(2, '0')
            }.${hundredths.toString().padStart(2, '0')}"
        } else if (minutes > 0) {
            "${minutes}:${seconds.toString().padStart(2, '0')}.${
                hundredths.toString().padStart(2, '0')
            }"
        } else {
            "${seconds}.${hundredths.toString().padStart(2, '0')}"
        }
    }

    fun toggleTimerState() {
        // TODO: check for inspection in settings
        // TODO: create new solve
        if (isRunning) {
            endTime = System.currentTimeMillis()
            isRunning = false
            _uiState.value = _uiState.value.copy(time = endTime - startTime)
            timerJob?.cancel()
        } else {
            startTime = System.currentTimeMillis()
            isRunning = true
            timerJob = viewModelScope.launch(dispatcher) {
                while (isRunning) {
                    _uiState.value = _uiState.value
                        .copy(time = System.currentTimeMillis() - startTime)
                    delay(10)
                }
            }
        }
    }

    fun changePenalty(penalty: Status) {
        _uiState.value = _uiState.value.copy(penalty = penalty)

        // TODO: update repository
    }

    fun deleteLastSolve() {
        if (lastSolve == null) return

        // TODO: delete from repository
    }
}

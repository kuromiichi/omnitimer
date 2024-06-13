package dev.kuromiichi.omnitimer.ui.screens.timer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.kuromiichi.omnitimer.data.models.Category
import dev.kuromiichi.omnitimer.data.models.Solve
import dev.kuromiichi.omnitimer.data.models.Status
import dev.kuromiichi.omnitimer.data.models.Subcategory
import dev.kuromiichi.omnitimer.data.repositories.SettingsRepository
import dev.kuromiichi.omnitimer.data.repositories.SettingsRepositoryImpl
import dev.kuromiichi.omnitimer.data.repositories.SolvesRepository
import dev.kuromiichi.omnitimer.data.repositories.SolvesRepositoryImpl
import dev.kuromiichi.omnitimer.data.repositories.SubcategoriesRepository
import dev.kuromiichi.omnitimer.data.repositories.SubcategoriesRepositoryImpl
import dev.kuromiichi.omnitimer.platform.toImageBitmap
import dev.kuromiichi.omnitimer.services.StatsService
import dev.kuromiichi.omnitimer.services.TNoodleService
import dev.kuromiichi.omnitimer.utils.getTimeStringFromMillis
import dev.kuromiichi.omnitimer.utils.now
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import java.util.UUID

class TimerViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState = _uiState.asStateFlow()

    private val settingsRepository: SettingsRepository = SettingsRepositoryImpl
    private val solvesRepository: SolvesRepository = SolvesRepositoryImpl
    private val subcategoryRepository: SubcategoriesRepository = SubcategoriesRepositoryImpl
    private val statsService = StatsService

    private var settings: Map<String, String> = settingsRepository.getSettings()
    val inspectionEnabled = settings["inspection"] == "true"

    val categories = Category.entries.map { it.displayName }

    private var time: Long = 0
    private var startTime: Long = 0
    private var endTime: Long = 0
    private var isPlusTwo: Boolean = false
    private var isDNF: Boolean = false

    private var lastSolve: Solve? = null

    private var timerJob: Job? = null
    private var inspectionJob: Job? = null

    init {
        _uiState.value = _uiState.value.copy(subcategory = getLastSubcategory())
        refreshScramble()
        generateStats()
    }

    private fun getLastSubcategory(): Subcategory {
        return settings["lastCategory"]?.let {
            subcategoryRepository.selectSubcategory(UUID.fromString(it))
        } ?: subcategoryRepository.selectSubcategoriesByCategory(Category.THREE).first()
    }

    private fun generateStats() {
        val stats = mutableMapOf<String, String>()

        if (settings["best"] == "true") {
            stats["Best time:"] =
                statsService.getBest(solvesRepository.getSessionBestSolve(uiState.value.subcategory))
        }
        if (settings["mo3"] == "true") {
            stats["mo3:"] = statsService.getMo3(
                solvesRepository.getSessionLastNSolves(3, uiState.value.subcategory)
                    .filter { !it.isArchived }
            )
        }
        if (settings["ao5"] == "true") {
            stats["ao5:"] = statsService.getAo5(
                solvesRepository.getSessionLastNSolves(5, uiState.value.subcategory)
                    .filter { !it.isArchived }
            )
        }
        if (settings["ao12"] == "true") {
            stats["ao12:"] = statsService.getAo12(
                solvesRepository.getSessionLastNSolves(12, uiState.value.subcategory)
                    .filter { !it.isArchived }
            )
        }
        if (settings["ao50"] == "true") {
            stats["ao50:"] = statsService.getAo50(
                solvesRepository.getSessionLastNSolves(50, uiState.value.subcategory)
                    .filter { !it.isArchived }
            )
        }
        if (settings["ao100"] == "true") {
            stats["ao100:"] =
                statsService.getAo100(
                    solvesRepository.getSessionLastNSolves(100, uiState.value.subcategory)
                        .filter { !it.isArchived }
                )
        }

        _uiState.value = _uiState.value.copy(stats = stats)
    }

    private fun setTime(time: Long) {
        this.time = time
        _uiState.value = _uiState.value.copy(timerText = getTime())
    }

    private fun getTime(): String {
        val currentSolve = lastSolve
        return when (uiState.value.timerState) {
            TimerState.Stopped -> if (currentSolve != null) {
                when (currentSolve.status) {
                    Status.PLUS_TWO -> "${getTimeStringFromMillis(currentSolve.time + 2000)}+"
                    Status.DNF -> "DNF (${getTimeStringFromMillis(currentSolve.time)})"
                    else -> getTimeStringFromMillis(currentSolve.time)
                }
            } else {
                if (isDNF) "DNF" else "0.00"
            }

            TimerState.Inspection -> {
                if (isPlusTwo) "+2" else "${time / 1000 - 1}"
            }

            TimerState.Running -> getTimeStringFromMillis(System.currentTimeMillis() - startTime)
        }
    }

    fun getSubcategories() =
        subcategoryRepository.selectSubcategoriesByCategory(uiState.value.subcategory.category)
            .map { it.name }


    fun onCategorySelectorClick() {
        _uiState.value = _uiState.value.copy(isCategoryDialogShowing = true)
    }

    fun onCategoryDialogDismiss() {
        _uiState.value = _uiState.value.copy(isCategoryDialogShowing = false)
    }

    fun onSubcategorySelectorClick() {
        _uiState.value = _uiState.value.copy(isSubcategoryDialogShowing = true)
    }

    fun onSubcategoryDialogDismiss() {
        _uiState.value = _uiState.value.copy(isSubcategoryDialogShowing = false)
    }

    fun onCategorySelected(category: String) {
        val subcategory =
            subcategoryRepository.selectLastSubcategory(Category.entries.find { it.displayName == category }!!)
                ?: subcategoryRepository.selectSubcategoriesByCategory(Category.entries.find { it.displayName == category }!!)
                    .first()
        settingsRepository.setSetting("lastCategory", subcategory.id.toString())
        subcategoryRepository.insertLastSubcategory(subcategory)
        _uiState.value = _uiState.value.copy(
            subcategory = subcategory,
            isCategoryDialogShowing = false
        )
        refreshScramble()
        generateStats()
    }

    fun onSubcategorySelected(subcategoryName: String) {
        val category = uiState.value.subcategory.category
        val subcategory = subcategoryRepository.selectSubcategoriesByCategory(category)
            .find { it.name == subcategoryName }
            ?: subcategoryRepository.selectSubcategoriesByCategory(category).first()
        settingsRepository.setSetting("lastCategory", subcategory.id.toString())
        subcategoryRepository.insertLastSubcategory(subcategory)
        _uiState.value = _uiState.value.copy(
            subcategory = subcategory,
            isSubcategoryDialogShowing = false
        )
        refreshScramble()
        generateStats()
    }

    fun onCreateSubcategoryClick() {
        _uiState.value = _uiState.value.copy(
            isCreateSubcategoryDialogShowing = true,
            subcategoryName = ""
        )
    }

    fun onEditSubcategoryClick(subcategoryName: String) {
        _uiState.value = _uiState.value.copy(
            isEditSubcategoryDialogShowing = true,
            originalSubcategoryName = subcategoryName,
            subcategoryName = subcategoryName
        )
    }

    fun onCreateSubcategoryDialogDismiss() {
        _uiState.value = _uiState.value.copy(isCreateSubcategoryDialogShowing = false)
    }

    fun onEditSubcategoryDialogDismiss() {
        _uiState.value = _uiState.value.copy(isEditSubcategoryDialogShowing = false)
    }

    fun onSubcategoryNameChange(name: String) {
        _uiState.value = _uiState.value.copy(subcategoryName = name)
    }

    fun onCreateSubcategoryConfirmClick() {
        if (uiState.value.subcategoryName.isBlank()) return
        if (subcategoryRepository.selectSubcategoriesByCategory(uiState.value.subcategory.category)
                .any { it.name == uiState.value.subcategoryName }
        ) return

        val category = uiState.value.subcategory.category
        subcategoryRepository.insertSubcategory(
            Subcategory(
                UUID.randomUUID(),
                uiState.value.subcategoryName,
                category
            )
        )
        onSubcategorySelected(uiState.value.subcategoryName)
        _uiState.value = _uiState.value.copy(isCreateSubcategoryDialogShowing = false)
    }

    fun onEditSubcategoryConfirmClick(originalSubcategoryName: String) {
        if (uiState.value.subcategoryName.isBlank()) return
        if (subcategoryRepository.selectSubcategoriesByCategory(uiState.value.subcategory.category)
                .any { it.name == uiState.value.subcategoryName }
        ) return

        val category = uiState.value.subcategory.category
        val originalSubcategory = subcategoryRepository.selectSubcategoriesByCategory(category)
            .find { it.name == originalSubcategoryName }

        originalSubcategory?.let {
            subcategoryRepository.updateSubcategory(
                Subcategory(
                    it.id,
                    uiState.value.subcategoryName,
                    category
                )
            )

            onSubcategorySelected(uiState.value.subcategoryName)
        }

        _uiState.value = _uiState.value.copy(
            isEditSubcategoryDialogShowing = false,
            isSubcategoryDialogShowing = true
        )
    }

    fun onDeleteSubcategoryClick() {
        if (subcategoryRepository
                .selectSubcategoriesByCategory(uiState.value.subcategory.category).size == 1
        ) return

        val subcategory =
            subcategoryRepository.selectSubcategoriesByCategory(uiState.value.subcategory.category)
                .find { it.name == uiState.value.originalSubcategoryName }

        if (subcategory == uiState.value.subcategory) return

        subcategory?.let {
            subcategoryRepository.deleteSubcategory(it)
        }

        _uiState.value = _uiState.value.copy(isEditSubcategoryDialogShowing = false)
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

    fun toggleTimerState() {
        when (uiState.value.timerState) {
            TimerState.Stopped -> {
                isDNF = false
                isPlusTwo = false
                lastSolve = null

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
            while (uiState.value.timerState == TimerState.Inspection && !isDNF) {
                if (System.currentTimeMillis() >= inspectionEnd) {
                    isDNF = true
                    isPlusTwo = false
                    _uiState.value = _uiState.value.copy(timerState = TimerState.Stopped)

                    solvesRepository.insertSolve(
                        Solve(
                            id = UUID.randomUUID(),
                            time = 0,
                            scramble = uiState.value.scramble,
                            status = Status.DNF,
                            date = LocalDateTime.now(),
                            subcategory = uiState.value.subcategory,
                            isArchived = false
                        )
                    )

                    _uiState.value = _uiState.value.copy(isAlerting = false)
                    refreshScramble()
                    generateStats()
                }
                if (inspectionEnd - System.currentTimeMillis() < 2000) {
                    isPlusTwo = true
                }
                if (
                    settings["alert"] == "true"
                    && inspectionEnd - System.currentTimeMillis() < 5000
                    && !uiState.value.isAlerting
                    && !isDNF
                ) {
                    _uiState.value = _uiState.value.copy(isAlerting = true)
                }
                setTime(inspectionEnd - System.currentTimeMillis())
                delay(10)
            }
        }
    }

    private fun startSolve() {
        if (uiState.value.timerState == TimerState.Inspection) {
            inspectionJob?.cancel()
        }
        _uiState.value = _uiState.value.copy(isAlerting = false)

        startTime = System.currentTimeMillis()
        _uiState.value = uiState.value.copy(timerState = TimerState.Running)

        timerJob = viewModelScope.launch(dispatcher) {
            while (uiState.value.timerState == TimerState.Running) {
                setTime(System.currentTimeMillis() - startTime)
                delay(10)
            }
        }
    }

    private fun finishSolve() {
        endTime = System.currentTimeMillis()
        val solveTime = endTime - startTime
        setTime(solveTime)
        _uiState.value = _uiState.value.copy(
            timerState = TimerState.Stopped,
            penalty = when {
                isPlusTwo -> Status.PLUS_TWO
                else -> Status.OK
            }
        )
        timerJob?.cancel()

        lastSolve = Solve(
            id = UUID.randomUUID(),
            time = solveTime,
            scramble = uiState.value.scramble,
            status = when {
                isPlusTwo -> Status.PLUS_TWO
                else -> Status.OK
            },
            date = LocalDateTime.now(),
            subcategory = uiState.value.subcategory,
            isArchived = false
        )

        lastSolve?.let { solvesRepository.insertSolve(it) }

        refreshScramble()
        generateStats()
    }

    fun changePenalty(penalty: Status) {
        lastSolve = lastSolve?.copy(status = penalty)
        lastSolve?.let { solvesRepository.updateSolve(it.copy(status = penalty)) }

        _uiState.value = _uiState.value.copy(
            penalty = penalty,
            timerText = getTime()
        )
        generateStats()
    }

    fun deleteLastSolve() {
        if (lastSolve == null) return

        lastSolve?.let { solvesRepository.deleteSolve(it) }
        lastSolve = null
        _uiState.value = _uiState.value.copy(
            penalty = Status.OK,
            timerText = getTime()
        )
        generateStats()
    }

    enum class TimerState {
        Stopped, Inspection, Running
    }
}

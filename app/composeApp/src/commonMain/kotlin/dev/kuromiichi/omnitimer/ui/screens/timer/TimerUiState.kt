package dev.kuromiichi.omnitimer.ui.screens.timer

import dev.kuromiichi.omnitimer.data.models.Category
import dev.kuromiichi.omnitimer.data.models.Scramble
import dev.kuromiichi.omnitimer.data.models.Status
import dev.kuromiichi.omnitimer.data.models.Subcategory
import java.util.UUID

data class TimerUiState(
    val subcategory: Subcategory = Subcategory(UUID.randomUUID(), "Default", Category.THREE),
    val scramble: Scramble = Scramble("", ""),
    val timerText: String = "0.00",
    val penalty: Status = Status.OK,
    val stats: Map<String, String> = mapOf(),
    val timerState: TimerViewModel.TimerState = TimerViewModel.TimerState.Stopped,
    val isCategoryDialogShowing: Boolean = false,
    val isSubcategoryDialogShowing: Boolean = false
)

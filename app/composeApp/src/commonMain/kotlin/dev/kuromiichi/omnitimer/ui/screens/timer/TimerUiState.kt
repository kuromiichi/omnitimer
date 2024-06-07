package ui.screens.timer

import dev.kuromiichi.omnitimer.data.models.Category
import dev.kuromiichi.omnitimer.data.models.Scramble
import dev.kuromiichi.omnitimer.data.models.Status
import dev.kuromiichi.omnitimer.data.models.Subcategory

data class TimerUiState(
    val subcategory: Subcategory = Subcategory("Normal", Category.THREE),
    val scramble: Scramble = Scramble("", ""),
    val time: Long = 0,
    val penalty: Status = Status.OK,
    val stats: Map<String, String> = mapOf()
)

package ui.screens.timer

import data.models.Category
import data.models.Scramble
import data.models.Status
import data.models.Subcategory

data class TimerUiState(
    val subcategory: Subcategory = Subcategory("Normal", Category.THREE),
    val scramble: Scramble = Scramble("", ""),
    val time: Long = 0,
    val penalty: Status = Status.OK,
    val stats: Map<String, String> = mapOf()
)

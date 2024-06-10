package dev.kuromiichi.omnitimer.ui.screens.list


import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.kuromiichi.omnitimer.data.models.Category
import dev.kuromiichi.omnitimer.data.models.Scramble
import dev.kuromiichi.omnitimer.data.models.Solve
import dev.kuromiichi.omnitimer.data.models.Status
import dev.kuromiichi.omnitimer.data.models.Subcategory
import dev.kuromiichi.omnitimer.services.TNoodleService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDateTime
import java.util.UUID

class ListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ListUiState())
    val uiState = _uiState.asStateFlow()

    var solves = listOf<Solve>(
        Solve(
            UUID.randomUUID(),
            2345,
            TNoodleService.getScramble(Category.THREE),
            Status.OK,
            LocalDateTime.parse("2022-01-01T00:00:00"),
            Subcategory("Normal", Category.THREE),
            false
        ),
        Solve(
            UUID.randomUUID(),
            2345,
            TNoodleService.getScramble(Category.THREE),
            Status.OK,
            LocalDateTime.parse("2022-01-01T00:00:00"),
            Subcategory("Normal", Category.THREE),
            false
        ),
        Solve(
            UUID.randomUUID(),
            2345,
            TNoodleService.getScramble(Category.THREE),
            Status.OK,
            LocalDateTime.parse("2022-01-01T00:00:00"),
            Subcategory("Normal", Category.THREE),
            false
        ),
    )
}

package dev.kuromiichi.omnitimer.ui.screens.list


import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.kuromiichi.omnitimer.data.models.Solve
import dev.kuromiichi.omnitimer.data.repositories.SolvesRepository
import dev.kuromiichi.omnitimer.data.repositories.SolvesRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ListUiState())
    val uiState = _uiState.asStateFlow()

    private val solvesRepository: SolvesRepository = SolvesRepositoryImpl
    val solves = listOf<Solve>()

    init {
        updateSolves()
    }

    private fun updateSolves() {
        val solves = solvesRepository.getSessionSolves(uiState.value.subcategory)
    }
}

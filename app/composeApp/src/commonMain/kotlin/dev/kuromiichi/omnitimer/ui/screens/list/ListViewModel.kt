package dev.kuromiichi.omnitimer.ui.screens.list


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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ListUiState())
    val uiState = _uiState.asStateFlow()

    private val solvesRepository: SolvesRepository = SolvesRepositoryImpl
    private val subcategoryRepository: SubcategoriesRepository = SubcategoriesRepositoryImpl
    private val settingsRepository: SettingsRepository = SettingsRepositoryImpl

    private var settings: Map<String, String> = settingsRepository.getSettings()

    val categories = Category.entries.map { it.displayName }

    var solves = mutableListOf<Solve>()

    init {
        _uiState.value = _uiState.value.copy(subcategory = getLastSubcategory())
        getSubcategories()
        refreshSolves()
    }

    fun getSubcategories() =
        subcategoryRepository.selectSubcategoriesByCategory(uiState.value.subcategory.category)
            .map { it.name }

    private fun getLastSubcategory(): Subcategory {
        return settings["lastCategory"]?.let {
            subcategoryRepository.selectSubcategory(UUID.fromString(it))
        } ?: subcategoryRepository.selectSubcategoriesByCategory(Category.THREE).first()
    }

    private fun refreshSolves() {
        if (uiState.value.showArchived) {
            solves = solvesRepository.getSolves(uiState.value.subcategory)
                .reversed()
                .toMutableList()
        } else {
            solves = solvesRepository.getSessionSolves(uiState.value.subcategory)
                .reversed()
                .toMutableList()
        }
        _uiState.value = _uiState.value.copy(count = solves.size)
    }

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
        refreshSolves()
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
        refreshSolves()
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

    fun changePenalty(solveIdx: Int, penalty: Status) {
        val solve = solves[solveIdx].copy(status = penalty)
        solvesRepository.updateSolve(solve)
        refreshSolves()
    }

    fun onDeleteClick(solve: Int) {
        solvesRepository.deleteSolve(solves[solve])
        refreshSolves()
    }

    fun toggleArchived() {
        _uiState.value = _uiState.value.copy(showArchived = !uiState.value.showArchived)
        refreshSolves()
    }

    fun onArchiveClick(solve: Int) {
        solvesRepository.updateSolve(solves[solve].copy(isArchived = true))
        refreshSolves()
    }
}

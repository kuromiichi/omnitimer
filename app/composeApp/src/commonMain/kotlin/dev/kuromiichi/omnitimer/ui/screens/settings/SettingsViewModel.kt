package dev.kuromiichi.omnitimer.ui.screens.settings

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.kuromiichi.omnitimer.data.dto.SolveDTO
import dev.kuromiichi.omnitimer.data.dto.toDTO
import dev.kuromiichi.omnitimer.data.dto.toSolve
import dev.kuromiichi.omnitimer.data.dto.toSubcategory
import dev.kuromiichi.omnitimer.data.repositories.SettingsRepository
import dev.kuromiichi.omnitimer.data.repositories.SettingsRepositoryImpl
import dev.kuromiichi.omnitimer.data.repositories.SolvesRepository
import dev.kuromiichi.omnitimer.data.repositories.SolvesRepositoryImpl
import dev.kuromiichi.omnitimer.data.repositories.SubcategoriesRepository
import dev.kuromiichi.omnitimer.data.repositories.SubcategoriesRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    private val settingsRepository: SettingsRepository = SettingsRepositoryImpl
    private val solvesRepository: SolvesRepository = SolvesRepositoryImpl
    private val subcategoryRepository: SubcategoriesRepository = SubcategoriesRepositoryImpl

    init {
        val settings = settingsRepository.getSettings().mapValues { it.value == "true" }
        _uiState.value = _uiState.value.copy(isEnabled = settings)
    }

    // Dialog-related functions
    fun onShowAccountDialogClick() {
        _uiState.value = _uiState.value.copy(isLoginDialogShowing = true)
    }

    fun onAccountDialogDismiss() {
        _uiState.value = _uiState.value.copy(
            email = "",
            password = "",
            passwordRepeat = "",
            name = "",
            isLoginDialogShowing = false,
            isLogin = true
        )
    }

    fun onDeleteSolvesButtonClick() {
        _uiState.value = _uiState.value.copy(isDeleteDialogShowing = true)
    }

    fun onDeleteSolvesDismissClick() {
        _uiState.value = _uiState.value.copy(isDeleteDialogShowing = false)
    }

    fun onDeleteSolvesConfirmClick() {
        solvesRepository.deleteAllSolves()
        _uiState.value = _uiState.value.copy(isDeleteDialogShowing = false)
    }

    // User action functions
    fun onLogoutClick() {
        _uiState.value = _uiState.value.copy(isUserLogged = false)

        // TODO: logout user
    }

    fun onLoginClick() {
        TODO("Not yet implemented")
    }

    fun onRegisterClick() {
        TODO("Not yet implemented")
    }

    fun onChangeToLoginClick() {
        _uiState.value = _uiState.value.copy(
            email = "",
            password = "",
            passwordRepeat = "",
            name = "",
            isLogin = true
        )
    }

    fun onChangeToRegisterClick() {
        _uiState.value = _uiState.value.copy(
            email = "",
            password = "",
            passwordRepeat = "",
            name = "",
            isLogin = false
        )
    }

    // User information change functions
    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            errorMessage = ""
        )
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            errorMessage = ""
        )
    }

    fun onPasswordRepeatChange(passwordRepeat: String) {
        _uiState.value = _uiState.value.copy(
            passwordRepeat = passwordRepeat,
            errorMessage = ""
        )
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(
            name = name,
            errorMessage = ""
        )
    }

    // Expandable settings and toggles
    fun onExpandedClick(item: String) {
        _uiState.value = _uiState.value.copy(
            isExpanded = _uiState.value.isExpanded
                .toMutableMap()
                .apply {
                    this[item] = !this[item]!!
                }
        )
    }

    fun onSettingToggle(toggle: String) {
        _uiState.value = _uiState.value.copy(
            isEnabled = _uiState.value.isEnabled
                .toMutableMap()
                .apply {
                    this[toggle] = !this[toggle]!!
                }
        )

        settingsRepository.setSetting(toggle, uiState.value.isEnabled[toggle]!!.toString())
    }

    fun onImportSolvesButtonClick(jsonField: String) {
        if (jsonField.isEmpty()) return
        try {
            val solves = Json.decodeFromString<List<SolveDTO>>(jsonField)
            solves.forEach { solve ->
                var subcategory = subcategoryRepository.selectSubcategoryByName(
                    solve.subcategory.name,
                    solve.subcategory.category
                )
                if (subcategory == null) {
                    subcategoryRepository.insertSubcategory(
                        solve.subcategory.toSubcategory()
                    )
                    subcategory = solve.subcategory.toSubcategory()
                }
                SolvesRepositoryImpl.insertSolve(solve.toSolve().copy(subcategory = subcategory))
            }

        } catch (e: SerializationException) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Invalid import data",
                jsonField = "INVALID JSON"
            )
        }
    }

    fun onExportSolvesButtonClick() {
        val solves = SolvesRepositoryImpl.getAllSolves()
        val solvesDTO = solves.map { it.toDTO() }
        val json = Json.encodeToString(solvesDTO)
        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(json), null)
        _uiState.value = _uiState.value.copy(isCopied = true)
    }

    fun onJsonFieldChange(jsonField: String) {
        _uiState.value = _uiState.value.copy(jsonField = jsonField)
    }
}

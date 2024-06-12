package dev.kuromiichi.omnitimer.ui.screens.settings

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.kuromiichi.omnitimer.data.repositories.SettingsRepository
import dev.kuromiichi.omnitimer.data.repositories.SettingsRepositoryImpl
import dev.kuromiichi.omnitimer.data.repositories.SolvesRepository
import dev.kuromiichi.omnitimer.data.repositories.SolvesRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    private val settingsRepository: SettingsRepository = SettingsRepositoryImpl
    private val solvesRepository: SolvesRepository = SolvesRepositoryImpl

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
}

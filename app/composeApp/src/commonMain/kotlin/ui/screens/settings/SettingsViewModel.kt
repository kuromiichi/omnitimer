package ui.screens.settings

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    // Dialog-related functions
    fun onDialogLoginClick() {
        _uiState.value = _uiState.value.copy(isLoginDialogShowing = true)
    }

    fun onLoginDialogDismissClick() {
        _uiState.value = _uiState.value.copy(isLoginDialogShowing = false)
    }

    fun onLoginDialogConfirmClick() {
        _uiState.value = _uiState.value.copy(isLoginDialogShowing = false)
    }

    fun onDeleteSolvesButtonClick() {
        _uiState.value = _uiState.value.copy(isDeleteDialogShowing = true)
    }

    fun onDeleteSolvesDismissClick() {
        _uiState.value = _uiState.value.copy(isDeleteDialogShowing = false)
    }

    fun onDeleteSolvesConfirmClick() {
        _uiState.value = _uiState.value.copy(isDeleteDialogShowing = false)
    }

    // User action functions
    fun onLogoutClick() {
        _uiState.value = _uiState.value.copy(isUserLogged = false)
    }

    fun onLoginClick() {
        TODO("Not yet implemented")
    }

    fun onRegisterClick() {
        if (uiState.value.password != uiState.value.passwordRepeat) {
            _uiState.value = _uiState.value.copy(errorMessage = "Passwords do not match")
            return
        }

        // TODO: register user
    }

    fun onChangeToLoginClick() {
        _uiState.value = _uiState.value.copy(isLogin = true)
    }

    fun onChangeToRegisterClick() {
        _uiState.value = _uiState.value.copy(isLogin = false)
    }

    // User information change functions
    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onPasswordRepeatChange(passwordRepeat: String) {
        _uiState.value = _uiState.value.copy(passwordRepeat = passwordRepeat)
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
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
    }
}

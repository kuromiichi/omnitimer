package ui.screens.settings

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

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

    fun onDeleteSolvesButtonClick() {
        _uiState.value = _uiState.value.copy(isDeleteDialogShowing = true)
    }

    fun onDeleteSolvesConfirmClick() {
        _uiState.value = _uiState.value.copy(isDeleteDialogShowing = false)
    }

    fun onDeleteSolvesDismissClick() {
        _uiState.value = _uiState.value.copy(isDeleteDialogShowing = false)
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onLoginClick() {
        TODO("Not yet implemented")
    }

    fun onRegisterClick() {
        TODO("Not yet implemented")
    }

    fun onChangeToRegisterClick() {
        _uiState.value = _uiState.value.copy(isLogin = false)
    }

    fun onChangeToLoginClick() {
        _uiState.value = _uiState.value.copy(isLogin = true)
    }

    fun onDialogLoginClick() {
        _uiState.value = _uiState.value.copy(isLoginDialogShowing = true)
    }

    fun onLogoutClick() {
        _uiState.value = _uiState.value.copy(isUserLogged = false)
    }

    fun onLoginDialogDismissClick() {
        _uiState.value = _uiState.value.copy(isLoginDialogShowing = false)
    }

    fun onLoginDialogConfirmClick() {
        _uiState.value = _uiState.value.copy(isLoginDialogShowing = false)
    }

}

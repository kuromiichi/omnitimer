package dev.kuromiichi.omnitimer.ui.screens.settings

data class SettingsUiState(
    val isExpanded: Map<String, Boolean> = mapOf(
        "account" to false,
        "inspection" to false,
        "stats" to false,
        "delete" to false,
        "import" to false,
        "export" to false
    ),
    val isEnabled: Map<String, Boolean> = mapOf(
        "inspection" to false,
        "alert" to false,
        "best" to false,
        "mo3" to false,
        "ao5" to false,
        "ao12" to false,
        "ao50" to false,
        "ao100" to false
    ),
    val isDeleteDialogShowing: Boolean = false,
    val email: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
    val name: String = "",
    val isLogin: Boolean = true,
    val isUserLogged: Boolean = false,
    val isLoginDialogShowing: Boolean = false,
    val errorMessage: String = "",
    val jsonField: String = "",
    val isCopied: Boolean = false,
)

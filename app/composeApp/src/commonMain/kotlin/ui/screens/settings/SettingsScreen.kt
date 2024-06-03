package ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import ui.composables.settings.AccountDialog
import ui.composables.settings.DeleteSolvesDialog
import ui.composables.settings.SettingsItem
import ui.composables.settings.SettingsToggle

@Composable
fun SettingsScreen() {
    val viewModel = getViewModel(Unit, viewModelFactory { SettingsViewModel() })
    val state: SettingsUiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .width(intrinsicSize = IntrinsicSize.Max)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SettingsItem(
                groupName = "Account settings",
                isExpanded = state.isExpanded["account"] ?: false,
                onExpandedClick = { viewModel.onExpandedClick("account") }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text =
                        if (state.isUserLogged) "Logged in as ${state.name}"
                        else "Not logged in, try logging in using the button below"
                    )
                    if (state.isUserLogged) {
                        Button(onClick = { viewModel.onLogoutClick() }) {
                            Text(text = "Logout")
                        }
                    } else {
                        Button(onClick = { viewModel.onShowAccountDialogClick() }) {
                            Text(text = "Login")
                        }
                    }
                    if (state.isLoginDialogShowing) {
                        AccountDialog(
                            isLogin = state.isLogin,
                            email = state.email,
                            password = state.password,
                            passwordRepeat = state.passwordRepeat,
                            name = state.name,
                            errorMessage = state.errorMessage,
                            onEmailChange = { viewModel.onEmailChange(it) },
                            onPasswordChange = { viewModel.onPasswordChange(it) },
                            onPasswordRepeatChange = { viewModel.onPasswordRepeatChange(it) },
                            onNameChange = { viewModel.onNameChange(it) },
                            onTopButtonClicked = {
                                if (state.isLogin) viewModel.onLoginClick()
                                else viewModel.onRegisterClick()
                            },
                            onBottomButtonClicked = {
                                if (state.isLogin) viewModel.onChangeToRegisterClick()
                                else viewModel.onChangeToLoginClick()
                            },
                            isOpen = state.isLoginDialogShowing,
                            onDismiss = { viewModel.onAccountDialogDismiss() },
                            onConfirm = { viewModel.onAccountDialogDismiss() },
                        )
                    }
                }
            }

            SettingsItem(
                groupName = "Inspection settings",
                isExpanded = state.isExpanded["inspection"] ?: false,
                onExpandedClick = { viewModel.onExpandedClick("inspection") }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SettingsToggle(
                        title = "Enable inspection",
                        enabled = state.isEnabled["inspection"] ?: false,
                        onToggle = { viewModel.onSettingToggle("inspection") }
                    )
                    SettingsToggle(
                        title = "Enable alert",
                        enabled = state.isEnabled["alert"] ?: false,
                        onToggle = { viewModel.onSettingToggle("alert") }
                    )
                }
            }

            SettingsItem(
                groupName = "Stats settings",
                isExpanded = state.isExpanded["stats"] ?: false,
                onExpandedClick = { viewModel.onExpandedClick("stats") }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SettingsToggle(
                        title = "Show best time",
                        enabled = state.isEnabled["best"] ?: false,
                        onToggle = { viewModel.onSettingToggle("best") }
                    )
                    SettingsToggle(
                        title = "Show mean of 3 (mo3)",
                        enabled = state.isEnabled["mo3"] ?: false,
                        onToggle = { viewModel.onSettingToggle("mo3") }
                    )
                    SettingsToggle(
                        title = "Show average of 5 (ao5)",
                        enabled = state.isEnabled["ao5"] ?: false,
                        onToggle = { viewModel.onSettingToggle("ao5") }
                    )
                    SettingsToggle(
                        title = "Show average of 12 (ao12)",
                        enabled = state.isEnabled["ao12"] ?: false,
                        onToggle = { viewModel.onSettingToggle("ao12") }
                    )
                    SettingsToggle(
                        title = "Show average of 50 (ao50)",
                        enabled = state.isEnabled["ao50"] ?: false,
                        onToggle = { viewModel.onSettingToggle("ao50") }
                    )
                    SettingsToggle(
                        title = "Show average of 100 (ao100)",
                        enabled = state.isEnabled["ao100"] ?: false,
                        onToggle = { viewModel.onSettingToggle("ao100") }
                    )
                }
            }

            SettingsItem(
                groupName = "Delete settings",
                isExpanded = state.isExpanded["delete"] ?: false,
                onExpandedClick = { viewModel.onExpandedClick("delete") }
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { viewModel.onDeleteSolvesButtonClick() },
                    ) {
                        Text("Delete all solves")
                    }
                }
            }

            DeleteSolvesDialog(
                isOpen = state.isDeleteDialogShowing,
                onConfirm = { viewModel.onDeleteSolvesConfirmClick() },
                onDismiss = { viewModel.onDeleteSolvesDismissClick() }
            )
        }
    }
}

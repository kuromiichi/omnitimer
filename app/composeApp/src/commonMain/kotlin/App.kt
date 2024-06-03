import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.screens.settings.SettingsScreen
import ui.theme.OmniTimerTheme


@Composable
@Preview
fun App() {
    OmniTimerTheme {
        /* APP CONTENT */
        SettingsScreen()
    }
}

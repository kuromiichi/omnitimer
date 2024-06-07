import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.navigation.SettingsTab
import ui.navigation.SolveListTab
import ui.navigation.TabItem
import ui.navigation.TimerTab
import ui.theme.OmniTimerTheme


@Composable
@Preview
fun App() {
    OmniTimerTheme {
        TabNavigator(TimerTab) {
            Scaffold(
                bottomBar = {
                    NavigationBar(

                    ) {
                        TabItem(SolveListTab)
                        TabItem(TimerTab)
                        TabItem(SettingsTab)
                    }
                }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    CurrentTab()
                }
            }
        }

    }
}



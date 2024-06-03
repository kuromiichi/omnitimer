import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.navigation.SettingsTab
import ui.navigation.SolveListTab
import ui.navigation.TimerTab
import ui.screens.timer.TimerScreen
import ui.theme.OmniTimerTheme


@Composable
@Preview
fun App() {
    OmniTimerTheme {
        TabNavigator(TimerTab){
            Scaffold(
                bottomBar = {
                    BottomNavigation {
                        TabItem(SolveListTab)
                        TabItem(TimerTab)
                        TabItem(SettingsTab)
                    }
                }
            ){
                CurrentTab()
            }
        }

    }
}

@Composable
private fun RowScope.TabItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = {
            tabNavigator.current = tab
        },
        icon = {
            tab.options.icon?.let { painter ->
                Icon(painter, contentDescription = tab.options.title)
            }
        }
    )
}

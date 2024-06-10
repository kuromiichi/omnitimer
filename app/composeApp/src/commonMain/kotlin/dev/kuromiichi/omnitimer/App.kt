package dev.kuromiichi.omnitimer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.kuromiichi.omnitimer.ui.navigation.SettingsTab
import dev.kuromiichi.omnitimer.ui.navigation.SolveListTab
import dev.kuromiichi.omnitimer.ui.navigation.TabItem
import dev.kuromiichi.omnitimer.ui.navigation.TimerTab
import dev.kuromiichi.omnitimer.ui.theme.OmniTimerTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    OmniTimerTheme {
        TabNavigator(TimerTab) {
            Scaffold(
                bottomBar = {
                    NavigationBar {
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



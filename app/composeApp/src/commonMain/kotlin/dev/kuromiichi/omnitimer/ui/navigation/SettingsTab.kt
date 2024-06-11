package dev.kuromiichi.omnitimer.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.kuromiichi.omnitimer.ui.screens.settings.SettingsScreen

object SettingsTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Settings)
            val title = "Settings"
            val index: UShort = 2u

            return TabOptions(
                icon = icon,
                title = title,
                index = index
            )
        }

    @Composable
    override fun Content() {
        SettingsScreen()
    }
}

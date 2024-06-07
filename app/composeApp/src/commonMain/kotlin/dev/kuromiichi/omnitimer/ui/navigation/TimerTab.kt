package ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ui.screens.timer.TimerScreen

object TimerTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Home)
            val title = "Timer"
            val index: UShort = 1u

            return TabOptions(
                icon = icon,
                title = title,
                index = index
            )
        }

    @Composable
    override fun Content() {
        TimerScreen()
    }
}

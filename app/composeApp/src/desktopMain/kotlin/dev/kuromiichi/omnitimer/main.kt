package dev.kuromiichi.omnitimer

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.kuromiichi.omnitimer.platform.DriverFactory
import dev.kuromiichi.omnitimer.platform.createDatabase
import dev.kuromiichi.omnitimer.services.DatabaseService

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "OmniTimer",
    ) {
        // Initialize database
        DatabaseService.db = createDatabase(DriverFactory())

        App()
    }
}
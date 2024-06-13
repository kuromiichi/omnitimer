package dev.kuromiichi.omnitimer

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.kuromiichi.omnitimer.platform.DriverFactory
import dev.kuromiichi.omnitimer.platform.createDatabase
import dev.kuromiichi.omnitimer.services.DatabaseService

fun main() = application {
    val icon = painterResource("drawable/app_icon.png")

    Window(
        onCloseRequest = ::exitApplication,
        title = "OmniTimer",
        icon = icon
    ) {
        // Initialize database
        DatabaseService.apply {
            db = createDatabase(DriverFactory())
            init()
        }

        App()
    }
}

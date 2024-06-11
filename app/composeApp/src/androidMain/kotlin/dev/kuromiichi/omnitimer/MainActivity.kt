package dev.kuromiichi.omnitimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.kuromiichi.omnitimer.platform.DriverFactory
import dev.kuromiichi.omnitimer.platform.createDatabase
import dev.kuromiichi.omnitimer.services.DatabaseService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database
        DatabaseService.apply {
            db = createDatabase(DriverFactory(this@MainActivity))
            init()
        }

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
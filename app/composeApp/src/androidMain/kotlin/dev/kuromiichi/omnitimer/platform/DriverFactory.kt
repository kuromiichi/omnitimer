package dev.kuromiichi.omnitimer.platform

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.kuromiichi.omnitimer.database.OmniTimerDatabase

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(OmniTimerDatabase.Schema, context, "omnitimer.db")
    }
}

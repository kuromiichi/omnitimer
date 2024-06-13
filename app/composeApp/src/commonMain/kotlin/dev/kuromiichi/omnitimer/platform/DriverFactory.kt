package dev.kuromiichi.omnitimer.platform

import app.cash.sqldelight.db.SqlDriver
import dev.kuromiichi.omnitimer.database.OmniTimerDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): OmniTimerDatabase {
    return OmniTimerDatabase(driverFactory.createDriver())
}

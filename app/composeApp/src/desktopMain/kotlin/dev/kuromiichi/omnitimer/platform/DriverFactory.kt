package dev.kuromiichi.omnitimer.platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.kuromiichi.omnitimer.database.OmniTimerDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        OmniTimerDatabase.Schema.create(driver)
        return driver
    }
}
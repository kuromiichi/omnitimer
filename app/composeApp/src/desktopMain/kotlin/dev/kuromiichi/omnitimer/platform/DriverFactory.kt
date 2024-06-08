package dev.kuromiichi.omnitimer.platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.kuromiichi.omnitimer.database.OmniTimerDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val path = "omnitimer.db"
        val driver = JdbcSqliteDriver("jdbc:sqlite:$path")
        OmniTimerDatabase.Schema.create(driver)
        return driver
    }
}
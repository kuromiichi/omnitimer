package dev.kuromiichi.omnitimer.services

import dev.kuromiichi.omnitimer.database.OmniTimerDatabase
import dev.kuromiichi.omnitimer.platform.DriverFactory
import dev.kuromiichi.omnitimer.platform.createDatabase

object DatabaseService {
    lateinit var db: OmniTimerDatabase
}
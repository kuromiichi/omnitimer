package dev.kuromiichi.omnitimer.data.repositories

import dev.kuromiichi.omnitimer.services.DatabaseService

object SettingsRepositoryImpl : SettingsRepository {
    private val db by lazy { DatabaseService.db }

    override fun getSettings(): Map<String, String> {
        val settings = mutableMapOf<String, String>()
        db.settingsQueries.selectSettings().executeAsList().forEach {
            settings[it.name] = it.value_
        }
        return settings
    }

    override fun getSetting(name: String): String? {
        return db.settingsQueries.selectSetting(name).executeAsOneOrNull()
    }

    override fun setSetting(name: String, value: String) {
        db.settingsQueries.updateSetting(value, name)
    }

}
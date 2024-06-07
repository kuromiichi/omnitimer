package dev.kuromiichi.omnitimer.data.repositories

interface SettingsRepository {
    fun getSettings(): Map<String, String>
    fun getSetting(name: String): String?
    fun setSetting(name: String, value: String)
}
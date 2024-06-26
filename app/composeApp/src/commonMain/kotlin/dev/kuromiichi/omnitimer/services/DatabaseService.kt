package dev.kuromiichi.omnitimer.services

import dev.kuromiichi.omnitimer.database.OmniTimerDatabase
import java.util.UUID

object DatabaseService {
    lateinit var db: OmniTimerDatabase
    fun init() {
        val categories = db.categoriesQueries.selectCategories().executeAsList()
        if (categories.isEmpty()) {
            listOf(
                "TWO",
                "THREE",
                "FOUR",
                "FIVE",
                "SIX",
                "SEVEN",
                "PYRA",
                "SQ1",
                "MEGA",
                "CLOCK",
                "SKEWB"
            ).forEach { name ->
                db.categoriesQueries.insertCategory(name)
                db.subcategoriesQueries.insertSubcategory(
                    UUID.randomUUID().toString(),
                    db.categoriesQueries.selectCategoryId(name).executeAsOne(),
                    "Default"
                )
            }
        }

        val settings = db.settingsQueries.selectSettings().executeAsList()
        if (settings.isEmpty()) {
            db.settingsQueries.insertSetting("inspection", "false")
            db.settingsQueries.insertSetting("alert", "false")
            db.settingsQueries.insertSetting("best", "false")
            db.settingsQueries.insertSetting("mo3", "false")
            db.settingsQueries.insertSetting("ao5", "false")
            db.settingsQueries.insertSetting("ao12", "false")
            db.settingsQueries.insertSetting("ao50", "false")
            db.settingsQueries.insertSetting("ao100", "false")
            db.settingsQueries.insertSetting(
                "lastCategory",
                db.subcategoriesQueries
                    .selectSubcategoriesByCategory(2)
                    .executeAsOne()
                    .id
            )
        }
    }
}

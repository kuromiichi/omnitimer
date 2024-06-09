package dev.kuromiichi.omnitimer.services

import dev.kuromiichi.omnitimer.database.OmniTimerDatabase
import dev.kuromiichi.omnitimer.platform.DriverFactory
import dev.kuromiichi.omnitimer.platform.createDatabase
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
    }
}
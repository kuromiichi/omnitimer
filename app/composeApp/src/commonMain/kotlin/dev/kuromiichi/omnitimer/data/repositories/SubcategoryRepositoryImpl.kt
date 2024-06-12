package dev.kuromiichi.omnitimer.data.repositories

import dev.kuromiichi.omnitimer.data.models.Category
import dev.kuromiichi.omnitimer.data.models.Subcategory
import dev.kuromiichi.omnitimer.services.DatabaseService
import java.util.UUID

object SubcategoryRepositoryImpl : SubcategoriesRepository {
    private val db by lazy { DatabaseService.db }

    override fun selectSubcategoriesByCategory(category: Category): List<Subcategory> {
        val categoryId = db.categoriesQueries.selectCategoryId(category.name).executeAsOne()
        return db.subcategoriesQueries.selectSubcategoriesByCategory(categoryId)
            .executeAsList()
            .map {
                Subcategory(
                    UUID.fromString(it.id),
                    it.name,
                    category
                )
            }
    }

    override fun selectSubcategory(id: UUID): Subcategory {
        val subcategory = db.subcategoriesQueries.selectSubcategory(id.toString()).executeAsOne()
        val categoryName =
            db.categoriesQueries.selectCategoryById(subcategory.category_id).executeAsOne()
        return Subcategory(
            id,
            subcategory.name,
            Category.valueOf(categoryName)
        )
    }

    override fun insertSubcategory(subcategory: Subcategory) {
        val categoryId =
            db.categoriesQueries.selectCategoryId(subcategory.category.name).executeAsOne()
        db.subcategoriesQueries.insertSubcategory(
            id = subcategory.id.toString(),
            category_id = categoryId,
            name = subcategory.name
        )
    }

    override fun updateSubcategory(subcategory: Subcategory) {
        db.subcategoriesQueries.updateSubcategory(
            name = subcategory.name,
            id = subcategory.id.toString()
        )
    }

    override fun deleteSubcategory(subcategory: Subcategory) {
        db.subcategoriesQueries.deleteSubcategory(id = subcategory.id.toString())
    }

    override fun selectLastSubcategory(category: Category): Subcategory? {
        val categoryId = db.categoriesQueries.selectCategoryId(category.name).executeAsOne()
        val lastSubcategoryId =
            db.subcategoriesQueries.selectLastSubcategory(categoryId)
                .executeAsOneOrNull()?.subcategory_id
        return lastSubcategoryId?.let { id ->
            db.subcategoriesQueries.selectSubcategory(id).executeAsOne().let {
                Subcategory(
                    UUID.fromString(id),
                    it.name,
                    category
                )
            }
        }
    }

    override fun insertLastSubcategory(subcategory: Subcategory) {
        val categoryId =
            db.categoriesQueries.selectCategoryId(subcategory.category.name).executeAsOne()
        db.subcategoriesQueries.insertLastSubcategory(
            category_id = categoryId,
            subcategory_id = subcategory.id.toString()
        )
    }
}


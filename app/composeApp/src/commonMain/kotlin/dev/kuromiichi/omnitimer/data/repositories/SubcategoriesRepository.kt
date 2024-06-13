package dev.kuromiichi.omnitimer.data.repositories

import dev.kuromiichi.omnitimer.data.models.Category
import dev.kuromiichi.omnitimer.data.models.Subcategory
import java.util.UUID

interface SubcategoriesRepository {
    fun selectSubcategoriesByCategory(category: Category): List<Subcategory>
    fun selectSubcategory(id: UUID): Subcategory?
    fun selectSubcategoryByName(name: String, category: Category): Subcategory?
    fun insertSubcategory(subcategory: Subcategory)
    fun updateSubcategory(subcategory: Subcategory)
    fun deleteSubcategory(subcategory: Subcategory)
    fun selectLastSubcategory(category: Category): Subcategory?
    fun insertLastSubcategory(subcategory: Subcategory)
}

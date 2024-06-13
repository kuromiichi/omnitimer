package dev.kuromiichi.omnitimer.data.dto

import dev.kuromiichi.omnitimer.data.models.Category
import dev.kuromiichi.omnitimer.data.models.Subcategory
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class SubcategoryDTO(
    val id: String,
    val name: String,
    val category: Category
)

fun SubcategoryDTO.toSubcategory() = Subcategory(
    id = UUID.fromString(id),
    name = name,
    category = category
)

fun Subcategory.toDTO() = SubcategoryDTO(
    id = id.toString(),
    name = name,
    category = category
)

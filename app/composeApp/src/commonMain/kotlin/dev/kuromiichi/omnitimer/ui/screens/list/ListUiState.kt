package dev.kuromiichi.omnitimer.ui.screens.list

import dev.kuromiichi.omnitimer.data.models.Category
import dev.kuromiichi.omnitimer.data.models.Subcategory
import java.util.UUID


data class ListUiState(
    val category: Category = Category.THREE,
    val subcategory: Subcategory = Subcategory(UUID.randomUUID(), "Default", Category.THREE),
    val sesionOnly: Boolean = false,
    val archivedOnly: Boolean = false
)

package dev.kuromiichi.omnitimer.ui.screens.list

import dev.kuromiichi.omnitimer.data.models.Category
import dev.kuromiichi.omnitimer.data.models.Subcategory


data class ListUiState (
    val category: Category = Category.THREE,
    val subcategory: Subcategory = Subcategory("Normal", Category.THREE),
    val sesionOnly: Boolean = false,
    val archivedOnly: Boolean = false
)

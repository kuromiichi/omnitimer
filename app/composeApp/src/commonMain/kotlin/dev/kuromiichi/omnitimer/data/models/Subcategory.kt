package dev.kuromiichi.omnitimer.data.models

import java.util.UUID

data class Subcategory(
    val id: UUID,
    val name: String,
    val category: Category
)

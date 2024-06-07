package dev.kuromiichi.omnitimer.data.models

import kotlinx.datetime.LocalDateTime
import java.util.UUID

data class Solve(
    val id: UUID,
    val time: Long,
    val scramble: Scramble,
    val status: Status,
    val date: LocalDateTime,
    val subcategory: Subcategory
)

package data.models

import kotlinx.datetime.LocalDateTime
import kotlin.time.Duration

data class Solve(
    val time: Duration,
    val scramble: Scramble,
    val status: Status,
    val date: LocalDateTime,
    val subcategory: Subcategory
)

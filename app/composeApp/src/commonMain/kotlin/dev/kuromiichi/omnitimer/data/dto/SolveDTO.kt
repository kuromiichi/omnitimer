package dev.kuromiichi.omnitimer.data.dto

import dev.kuromiichi.omnitimer.data.models.Scramble
import dev.kuromiichi.omnitimer.data.models.Solve
import dev.kuromiichi.omnitimer.data.models.Status
import dev.kuromiichi.omnitimer.services.TNoodleService
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class SolveDTO(
    val id: String,
    val time: Long,
    val scramble: String,
    val status: Status,
    val date: LocalDateTime,
    val subcategory: SubcategoryDTO,
    val isArchived: Boolean
)

fun SolveDTO.toSolve() = Solve(
    id = UUID.fromString(id),
    time = time,
    scramble = Scramble(
        scramble,
        TNoodleService.getImageFromScramble(scramble, subcategory.toSubcategory().category)
    ),
    status = status,
    date = date,
    subcategory = subcategory.toSubcategory(),
    isArchived = isArchived
)

fun Solve.toDTO() = SolveDTO(
    id = id.toString(),
    time = time,
    scramble = scramble.value,
    status = status,
    date = date,
    subcategory = subcategory.toDTO(),
    isArchived = isArchived
)

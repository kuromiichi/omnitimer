package dev.kuromiichi.omnitimer.data.repositories

import dev.kuromiichi.omnitimer.data.models.Category
import dev.kuromiichi.omnitimer.data.models.Scramble
import dev.kuromiichi.omnitimer.data.models.Solve
import dev.kuromiichi.omnitimer.data.models.Status
import dev.kuromiichi.omnitimer.data.models.Subcategory
import dev.kuromiichi.omnitimer.services.DatabaseService
import kotlinx.datetime.LocalDateTime
import java.util.UUID

object SolvesRepositoryImpl : SolvesRepository {
    private val db by lazy { DatabaseService.db }

    private fun getSubcategory(id: String): Subcategory {
        return db.subcategoriesQueries.selectSubcategory(id).executeAsOne().let {
            Subcategory(
                UUID.fromString(it.id),
                it.name,
                Category.valueOf(
                    db.categoriesQueries.selectCategory(it.category_id).executeAsOne()
                )
            )
        }
    }

    private fun getSubcategoryId(subcategory: Subcategory): String {
        val categoryId = db.categoriesQueries
            .selectCategoryId(subcategory.category.name)
            .executeAsOne()
        val subcategoryId = db.subcategoriesQueries
            .selectSubcategoryId(categoryId, subcategory.name)
            .executeAsOne()

        return subcategoryId
    }

    private fun getSolvesInSubcategory(subcategory: Subcategory): List<Solve> {
        val subcategoryId = getSubcategoryId(subcategory)

        return db.solvesQueries
            .selectSolves(subcategoryId)
            .executeAsList()
            .map {
                Solve(
                    UUID.fromString(it.id),
                    it.time,
                    Scramble(it.scramble, it.image),
                    Status.valueOf(it.status),
                    LocalDateTime.parse(it.date),
                    subcategory,
                    it.is_archived == 1L
                )
            }
    }

    override fun getSolves(subcategory: Subcategory) = getSolvesInSubcategory(subcategory)

    override fun getSessionSolves(subcategory: Subcategory) =
        getSolvesInSubcategory(subcategory).filter { !it.isArchived }

    override fun getSessionBestSolve(subcategory: Subcategory): Solve? {
        val solves = getSolvesInSubcategory(subcategory)

        if (solves.isEmpty()) return null
        if (solves.all { it.status == Status.DNF }) return solves.first()

        val bestSolve = solves
            .filter { !it.isArchived }
            .filter { it.status != Status.DNF }
            .map {
                when (it.status) {
                    Status.PLUS_TWO -> it.copy(time = it.time + 2000)
                    else -> it
                }
            }.minByOrNull { it.time }

        return bestSolve
    }

    override fun getSessionLastNSolves(n: Int, subcategory: Subcategory): List<Solve> {
        val solves = getSolvesInSubcategory(subcategory)

        return solves.filter { !it.isArchived }.sortedBy { it.date }.takeLast(n)
    }

    override fun insertSolve(solve: Solve) {
        db.solvesQueries.insertSolve(
            id = solve.id.toString(),
            time = solve.time,
            scramble = solve.scramble.value,
            image = solve.scramble.image,
            status = solve.status.name,
            date = solve.date.toString(),
            subcategory_id = getSubcategoryId(solve.subcategory),
            is_archived = if (solve.isArchived) 1L else 0L
        )
    }

    override fun updateSolve(solve: Solve) {
        db.solvesQueries.updateSolve(
            time = solve.time,
            scramble = solve.scramble.value,
            image = solve.scramble.image,
            status = solve.status.name,
            date = solve.date.toString(),
            subcategory_id = getSubcategoryId(solve.subcategory),
            is_archived = if (solve.isArchived) 1L else 0L,
            id = solve.id.toString()
        )
    }

    override fun deleteSolve(solve: Solve) {
        db.solvesQueries.deleteSolve(id = solve.id.toString())
    }

    override fun getAllSolves(): List<Solve> {
        return db.solvesQueries.selectAllSolves().executeAsList().map {
            Solve(
                UUID.fromString(it.id),
                it.time,
                Scramble(it.scramble, it.image),
                Status.valueOf(it.status),
                LocalDateTime.parse(it.date),
                getSubcategory(it.subcategory_id),
                it.is_archived == 1L
            )
        }
    }
}

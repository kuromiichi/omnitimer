package dev.kuromiichi.omnitimer.data.repositories

import dev.kuromiichi.omnitimer.data.models.Scramble
import dev.kuromiichi.omnitimer.data.models.Solve
import dev.kuromiichi.omnitimer.data.models.Status
import dev.kuromiichi.omnitimer.data.models.Subcategory
import dev.kuromiichi.omnitimer.services.DatabaseService
import kotlinx.datetime.LocalDateTime
import java.util.UUID

object SolvesRepositoryImpl : SolvesRepository {
    private val db by lazy { DatabaseService.db }

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
                    subcategory
                )
            }
    }

    override fun getSolves(subcategory: Subcategory) = getSolvesInSubcategory(subcategory)

    override fun getBestSolve(subcategory: Subcategory): Solve? {
        val solves = getSolvesInSubcategory(subcategory)

        if (solves.isEmpty()) return null
        if (solves.all { it.status == Status.DNF }) return solves.first()

        val bestSolve = solves
            .filter { it.status != Status.DNF }
            .map {
                when (it.status) {
                    Status.PLUS_TWO -> it.copy(time = it.time + 2000)
                    else -> it
                }
            }.minByOrNull { it.time }

        return bestSolve
    }

    override fun getLastNSolves(n: Int, subcategory: Subcategory): List<Solve> {
        val solves = getSolvesInSubcategory(subcategory)

        return solves.sortedBy { it.time }.takeLast(n)
    }
}
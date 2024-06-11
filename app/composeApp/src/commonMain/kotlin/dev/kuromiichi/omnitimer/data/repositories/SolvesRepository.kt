package dev.kuromiichi.omnitimer.data.repositories

import dev.kuromiichi.omnitimer.data.models.Solve
import dev.kuromiichi.omnitimer.data.models.Subcategory

interface SolvesRepository {
    fun getSolves(subcategory: Subcategory): List<Solve>
    fun getBestSolve(subcategory: Subcategory): Solve?
    fun getLastNSolves(n: Int, subcategory: Subcategory): List<Solve>
    fun insertSolve(solve: Solve)
    fun updateSolve(solve: Solve)
    fun deleteSolve(solve: Solve)
}
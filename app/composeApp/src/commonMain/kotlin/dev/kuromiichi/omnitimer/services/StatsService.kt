package dev.kuromiichi.omnitimer.services

import dev.kuromiichi.omnitimer.data.models.Solve
import dev.kuromiichi.omnitimer.data.models.Status
import dev.kuromiichi.omnitimer.utils.getTimeStringFromMillis

object StatsService {
    private fun getAvg(n: Int, solves: List<Solve>): String {
        if (solves.size < n) return "-"

        return when (solves.count { it.status == Status.DNF }) {
            0 -> getTimeStringFromMillis(
                solves.map { if (it.status == Status.PLUS_TWO) it.copy(time = it.time + 2000) else it }
                    .map { it.time }
                    .sorted()
                    .drop(1)
                    .dropLast(1)
                    .average()
                    .toLong()
            )

            1 -> getTimeStringFromMillis(
                solves.map { if (it.status == Status.PLUS_TWO) it.copy(time = it.time + 2000) else it }
                    .filter { it.status != Status.DNF }
                    .map { it.time }
                    .sorted()
                    .dropLast(1)
                    .average()
                    .toLong()
            )

            else -> "DNF"
        }

    }

    fun getBest(bestSolve: Solve?): String {
        if (bestSolve == null) return "-"
        if (bestSolve.status == Status.DNF) return "DNF"
        return getTimeStringFromMillis(bestSolve.time)
    }

    fun getMo3(solves: List<Solve>): String {
        if (solves.size < 3) return "-"
        if (solves.any { it.status == Status.DNF }) return "DNF"
        val mean = solves.map { if (it.status == Status.PLUS_TWO) it.copy(time = it.time + 2000) else it }
            .map { it.time }.average().toLong()
        return getTimeStringFromMillis(mean)
    }

    fun getAo5(solves: List<Solve>): String {
        if (solves.size < 5) return "-"
        return getAvg(5, solves)
    }

    fun getAo12(solves: List<Solve>): String {
        if (solves.size < 12) return "-"
        return getAvg(12, solves)
    }

    fun getAo50(solves: List<Solve>): String {
        if (solves.size < 50) return "-"
        return getAvg(50, solves)
    }

    fun getAo100(solves: List<Solve>): String {
        if (solves.size < 100) return "-"
        return getAvg(100, solves)
    }
}
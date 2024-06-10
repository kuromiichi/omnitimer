package dev.kuromiichi.omnitimer.services

import dev.kuromiichi.omnitimer.data.models.Solve
import dev.kuromiichi.omnitimer.utils.getTimeStringFromMillis

object StatsService {
    fun getBest(bestSolve: Solve?) = when (bestSolve) {
        null -> "-"
        else -> getTimeStringFromMillis(bestSolve.time)
    }

    fun getMo3(solves: List<Solve>): String {
        if (solves.size < 3) return "-"
        val mean = solves.map { it.time }.average().toLong()
        return getTimeStringFromMillis(mean)
    }

    fun getAo5(solves: List<Solve>): String {
        if (solves.size < 5) return "-"
        val avg = solves.map { it.time }.sorted().drop(1).dropLast(1).average().toLong()
        return getTimeStringFromMillis(avg)
    }

    fun getAo12(solves: List<Solve>): String {
        if (solves.size < 12) return "-"
        val avg = solves.map { it.time }.sorted().drop(1).dropLast(1).average().toLong()
        return getTimeStringFromMillis(avg)
    }

    fun getAo50(solves: List<Solve>): String {
        if (solves.size < 50) return "-"
        val avg = solves.map { it.time }.sorted().drop(1).dropLast(1).average().toLong()
        return getTimeStringFromMillis(avg)
    }

    fun getAo100(solves: List<Solve>): String {
        if (solves.size < 100) return "-"
        val avg = solves.map { it.time }.sorted().drop(1).dropLast(1).average().toLong()
        return getTimeStringFromMillis(avg)
    }
}
package dev.kuromiichi.omnitimer.services

import dev.kuromiichi.omnitimer.data.models.Category
import dev.kuromiichi.omnitimer.data.models.Scramble
import org.worldcubeassociation.tnoodle.scrambles.PuzzleRegistry

object TNoodleService {
    fun getScramble(category: Category): Scramble {
        val scrambler = PuzzleRegistry.valueOf(category.name).scrambler

        val scramble = scrambler.generateScramble()
        val image = scrambler.drawScramble(scramble, scrambler.defaultColorScheme)

        return Scramble(scramble, image.toString())
    }
}

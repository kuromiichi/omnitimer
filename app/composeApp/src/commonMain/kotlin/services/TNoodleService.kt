package services

import data.models.Category
import data.models.Scramble
import org.worldcubeassociation.tnoodle.scrambles.PuzzleRegistry

object TNoodleService {
    fun getScramble(category: Category): Scramble {
        val scrambler = PuzzleRegistry.valueOf(category.name).scrambler

        val scramble = scrambler.generateScramble()
        val image = scrambler.drawScramble(scramble, scrambler.defaultColorScheme)

        return Scramble(scramble, image.toString())
    }
}

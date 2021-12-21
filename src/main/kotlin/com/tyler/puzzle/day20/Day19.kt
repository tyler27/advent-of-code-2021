package com.tyler.puzzle.day20

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day20 : Puzzle<Int?> {
    private val lines = Day20::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()

    private val imageEnhancementAlgorithm = lines.first()

    private fun lookup(vararg key: Char): Char = imageEnhancementAlgorithm[key.fold(0) { acc, c -> 2 * acc + c.code.and(1) }]

    private val generatedImages = generateSequence(lines.drop(2) to '.') { (image, fill) ->
        List(image.size + 2) { y ->
            val line1 = image.getOrNull(y - 2)
            val line2 = image.getOrNull(y - 1)
            val line3 = image.getOrNull(y)
            CharArray(image[(y - 1).coerceIn(image.indices)].length + 2) { x ->
                lookup(
                    line1?.getOrNull(x - 2) ?: fill, line1?.getOrNull(x - 1) ?: fill, line1?.getOrNull(x) ?: fill,
                    line2?.getOrNull(x - 2) ?: fill, line2?.getOrNull(x - 1) ?: fill, line2?.getOrNull(x) ?: fill,
                    line3?.getOrNull(x - 2) ?: fill, line3?.getOrNull(x - 1) ?: fill, line3?.getOrNull(x) ?: fill,
                )
            }.concatToString()
        } to lookup(fill, fill, fill, fill, fill, fill, fill, fill, fill)
    }.map { (image, fill) -> if (fill == '.') image else null }

    private companion object {
        const val INPUT_FILE = "/day20/input.txt"
    }

    override fun solvePartOne(): Int? {
        return generatedImages.drop(2).firstOrNull()?.sumOf { it.count { char -> char == '#' } }
    }

    override fun solvePartTwo(): Int? {
        return generatedImages.drop(50).firstOrNull()?.sumOf { it.count { char -> char == '#' } }
    }
}

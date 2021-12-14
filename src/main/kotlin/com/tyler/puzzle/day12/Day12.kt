package com.tyler.puzzle.day12

import com.tyler.puzzle.Puzzle
import java.util.*

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day12: Puzzle<Int> {
    private val input = Day12::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()
        .map { it.split("-") }
    private var count = -1

    override fun solvePartOne(): Int {
        count = 0
        searchPaths(START_LABEL, "$START_LABEL,", false)
        return count
    }

    private fun searchPaths(label: String, path: String, partTwo: Boolean) {
        var locked = false

        if (partTwo) {
            val small = path.split(",").filter { el -> el.lowercase(Locale.getDefault()) == el }

            small.forEach { el ->
                if (small.filter { n -> n == el }.size > 1) {
                    locked = true
                }
            }
        }

        val filters = input.filter { row -> row[0] == label || row[1] == label }.map { row ->
            if (row[0] == label) row[1] else row[0]
        }

        filters.forEach { nextLabel ->
            val isSmall = nextLabel.lowercase(Locale.getDefault()) == nextLabel

            if (nextLabel == END_LABEL) count++

            when {
                nextLabel != START_LABEL && nextLabel != END_LABEL -> {
                    val shouldSearch = (isSmall && path.split(DELIMITER).indexOf(nextLabel) == -1) || !isSmall
                    val isBig = !isSmall ||!locked ||(locked && path.split(",").indexOf(nextLabel) == -1)

                    when {
                        partTwo -> {
                            if (isBig) {
                                searchPaths(nextLabel, "$path${nextLabel},", true)
                            }
                        }
                        shouldSearch -> {
                            searchPaths(nextLabel, "$path${nextLabel},", false)
                        }
                    }
                }
            }
        }
    }

    override fun solvePartTwo(): Int {
        count = 0
        searchPaths(START_LABEL, "$START_LABEL,", true)
        return count
    }

    private companion object {
        const val INPUT_FILE = "/day12/input.txt"
        const val START_LABEL = "start"
        const val END_LABEL = "end"
        const val DELIMITER = ","
    }
}
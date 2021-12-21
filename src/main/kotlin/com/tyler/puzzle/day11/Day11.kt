package com.tyler.puzzle.day11

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day11 : Puzzle<Int> {
    private val input = Day11::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()

    override fun solvePartOne(): Int {
        val octopusMap = mapOctopuses()

        (1..100).forEach { currentStep ->
            octopusMap.values.forEach { it.handleStep(currentStep) }
            flashAdjacentOctopuses(octopusMap, currentStep)
        }
        return octopusMap.values.sumOf(Octopus::counter)
    }

    override fun solvePartTwo(): Int {
        val octopusMap = mapOctopuses()

        (1..Int.MAX_VALUE).forEach { step ->
            octopusMap.values.forEach { it.handleStep(step) }
            flashAdjacentOctopuses(octopusMap, step)
            if (octopusMap.values.all { it.last == step }) {
                return step
            }
        }
        return 0
    }

    private fun mapOctopuses() = input.flatMapIndexed { row, line ->
        line.mapIndexed { col, char -> Pair(row, col) to Octopus(Pair(row, col), char.digitToInt()) }
    }.toMap()

    private fun flashAdjacentOctopuses(
        map: Map<Pair<Int, Int>, Octopus>,
        step: Int
    ) {
        while (map.values.any { it.shouldFlash(step) }) {
            map.values.filter { it.shouldFlash(step) }.forEach {
                if (it.flash(step)) {
                    getAdjacentPointsIncludeDiagonals(it.location).forEach { point ->
                        map.getOrDefault(point, null)?.handleStep(step)
                    }
                }
            }
        }
    }

    private fun getAdjacentPointsIncludeDiagonals(point: Pair<Int, Int>): List<Pair<Int, Int>> {
        return setOf(
            point.first + 1 to point.second,
            point.first - 1 to point.second,
            point.first to point.second + 1,
            point.first to point.second - 1,
            point.first + 1 to point.second + 1,
            point.first + 1 to point.second - 1,
            point.first - 1 to point.second + 1,
            point.first - 1 to point.second - 1,
        ).toList()
    }

    private companion object {
        const val INPUT_FILE = "/day11/input.txt"
    }
}

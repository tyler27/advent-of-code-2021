package com.tyler.puzzle.day05

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day5 : Puzzle<Int> {
    private val numbersToPlot = input.map {
        it.trim().replace(" -> ", ",").split(",").map(String::toInt)
    }

    override fun solvePartOne(): Int {
        return plotLines(false).entries.count { it.value > 0 }
    }

    override fun solvePartTwo(): Int {
        return plotLines(true).entries.count { it.value > 0 }
    }

    private fun plotLines(includeDiagonal: Boolean): MutableMap<Pair<Int, Int>, Int> {
        val lines = mutableMapOf<Pair<Int, Int>, Int>()

        numbersToPlot.map {
            val (x1, y1, x2, y2) = it

            if (x1 == x2) plotHorizontalLine(lines, x1, y1, y2)
            else if (y1 == y2) plotVerticalLines(lines, x1, y1, x2)
            else if (includeDiagonal) plotDiagonalLines(lines, x1, y1, x2, y2)
        }

        return lines
    }

    private fun plotDiagonalLines(
        lines: MutableMap<Pair<Int, Int>, Int>,
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int
    ) {
        var x = x1
        var y = y1

        while (true) {
            val point = x to y
            plotLine(lines, point)

            if (x == x2)
                break

            x = if (x2 > x) ++x else --x
            y = if (y2 > y) ++y else --y
        }
    }

    private fun plotVerticalLines(
        lines: MutableMap<Pair<Int, Int>, Int>,
        x1: Int,
        y1: Int,
        x2: Int
    ) {
        val minX = x1.coerceAtMost(x2)
        val maxX = x1.coerceAtLeast(x2)

        (minX..maxX).forEach { x ->
            val point = x to y1
            plotLine(lines, point)
        }
    }

    private fun plotHorizontalLine(lines: MutableMap<Pair<Int, Int>, Int>, x1: Int, y1: Int, y2: Int) {
        val minY = y1.coerceAtMost(y2)
        val maxY = y1.coerceAtLeast(y2)

        (minY..maxY).forEach { y ->
            val point = x1 to y
            plotLine(lines, point)
        }
    }

    private fun plotLine(
        lines: MutableMap<Pair<Int, Int>, Int>,
        point: Pair<Int, Int>
    ) {
        if (!lines.contains(point)) lines[point] = -1
        lines[point] = lines[point]!! + 1
    }

    private companion object {
        const val INPUT_FILE = "/day5/input.txt"
        val input = Day5::class.java.getResourceAsStream(INPUT_FILE)!!.bufferedReader().readLines()
    }
}
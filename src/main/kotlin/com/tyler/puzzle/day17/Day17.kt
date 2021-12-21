package com.tyler.puzzle.day17

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day17 : Puzzle<Any> {
    private val input = Day17::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()

    private fun launchProbe(vX: Int, vY: Int, targetX: IntRange, targetY: IntRange): Pair<Boolean, Int> {
        var (x, y, velocityX, velocityY) = listOf(0, 0, vX, vY)
        var maxY = Int.MIN_VALUE
        while (!(x in targetX && y in targetY) && x <= targetX.last && y >= targetY.first) {
            x += velocityX
            y += velocityY
            if (y > maxY) maxY = y
            velocityX += if (velocityX > 0) -1 else if (velocityX < 0) 1 else 0
            velocityY--
        }
        return Pair((x in targetX && y in targetY), maxY)
    }

    var maxHeight = Int.MIN_VALUE
    var steps = 0

    private fun solution(inputs: List<String>) {
        val (first, second) = inputs.first().removePrefix("target area:").split(",").map(String::trim)
        val (xMin, xMax) = first.removePrefix("x=").split("..").map { it.trim().toInt() }
        val (yMin, yMax) = second.removePrefix("y=").split("..").map { it.trim().toInt() }

        for (xVel in 1..xMax) {
            for (yVel in yMin..200) {
                val (isInRange, value) = launchProbe(xVel, yVel, xMin..xMax, yMin..yMax)
                if (isInRange) {
                    steps++
                    if (value > maxHeight) {
                        maxHeight = value
                    }
                }
            }
        }
    }

    private companion object {
        const val INPUT_FILE = "/day17/input.txt"
    }

    override fun solvePartOne(): Any {
        solution(input)
        return maxHeight
    }

    override fun solvePartTwo(): Any {
       return steps
    }
}

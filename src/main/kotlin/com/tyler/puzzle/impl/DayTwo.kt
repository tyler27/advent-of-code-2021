package com.tyler.puzzle.impl

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class DayTwo: Puzzle<Any> {
    override fun solvePartOne(): Any {
        var horizontalPosition = 0
        var depth = 0

        input.forEach {
            val commandSplit = it.split(" ")

            val command = commandSplit[0]
            val value = commandSplit[1].toInt()

            when (command) {
                "forward" -> {
                    horizontalPosition += value
                }
                "down" -> {
                    depth += value
                }
                "up" -> {
                    depth -= value
                }
            }
        }
        return horizontalPosition * depth
    }

    override fun solvePartTwo(): Any {
        var horizontalPosition = 0
        var depth = 0
        var aim = 0

        input.forEach {
            val commandSplit = it.split(" ")

            val command = commandSplit[0]
            val value = commandSplit[1].toInt()

            when (command) {
                "forward" -> {
                    horizontalPosition += value
                    depth  += (aim * value)
                }
                "down" -> {
                    aim += value
                }
                "up" -> {
                    aim -= value
                }
            }
        }

        return horizontalPosition * depth
    }

    private companion object {
        const val INPUT_FILE = "/day_two/input.txt"
        val input = DayTwo::class.java.getResourceAsStream(INPUT_FILE)!!.bufferedReader().readLines()
    }
}
package com.tyler.puzzle.day02

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day2: Puzzle<Int> {
    override fun solvePartOne(): Int {
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

    override fun solvePartTwo(): Int {
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
        const val INPUT_FILE = "/day2/input.txt"
        val input = Day2::class.java.getResourceAsStream(INPUT_FILE)!!.bufferedReader().readLines()
    }
}
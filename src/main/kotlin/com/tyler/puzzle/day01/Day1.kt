package com.tyler.puzzle.day01

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day1: Puzzle<Int> {
    override fun solvePartOne(): Int {
        return count(input)
    }

    override fun solvePartTwo(): Int {
        val window = input.windowed(3).map { list -> list.sum() }
        return count(window)
    }

    private fun count(input: List<Int>): Int {
        var previous = -1
        var increased = 0

        input.forEach {
            if (it > previous && previous != -1) increased++
            previous = it
        }
        return increased
    }

    private companion object {
        const val INPUT_FILE = "/day1/input.txt"
        val input = Day1::class.java.getResourceAsStream(INPUT_FILE)!!.bufferedReader().readLines().map { it.toInt() }
    }
}
package com.tyler.puzzle.impl

import com.tyler.puzzle.Puzzle

class DayOne: Puzzle<Int> {
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
        const val INPUT_FILE = "/day_one/input.txt"
        val input = DayOne::class.java.getResourceAsStream(INPUT_FILE)!!.bufferedReader().readLines().map { it.toInt() }
    }
}
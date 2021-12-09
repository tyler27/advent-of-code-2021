package com.tyler.puzzle.day9

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day9: Puzzle<Int> {
    private val input = Day9::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()

    override fun solvePartOne(): Int {
        return 0
    }

    override fun solvePartTwo(): Int {
        return 0
    }

    private companion object {
        const val INPUT_FILE = "/day9/input.txt"
    }
}
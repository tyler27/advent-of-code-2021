package com.tyler.puzzle.day7

import com.tyler.puzzle.Puzzle
import com.tyler.puzzle.day6.Day6
import kotlin.math.abs

class Day7: Puzzle<Int> {
    private val input = Day6::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()
        .first()
        .splitToSequence(",")
        .map(String::toInt)
        .sorted()
        .toList()

    override fun solvePartOne(): Int {
        return input.sumOf { abs(input[input.size / 2] - it) }
    }

    override fun solvePartTwo(): Int {
        return (0..input.maxOf { it }).minOf {
            input.sumOf { alignment -> ((1 + abs(it - alignment)) * abs(it - alignment) / 2) }
        }
    }

    private companion object {
        const val INPUT_FILE = "/day7/input.txt"
    }
}
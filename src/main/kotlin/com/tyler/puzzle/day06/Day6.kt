package com.tyler.puzzle.day06

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day6 : Puzzle<Long> {
    private var lanternFish = input
        .first()
        .split(",")
        .map(String::toInt)
        .groupBy { it }
        .mapValues { it.value.size.toLong() }
        .toMutableMap()

    override fun solvePartOne(): Long {
        return simulateFish(80)
    }

    override fun solvePartTwo(): Long {
        return simulateFish(256)
    }

    private fun simulateFish(days: Int): Long {
        repeat(days) {
            val updates = lanternFish.map { (internalTimer, count) -> ageFish(internalTimer, count) } + (8 to (lanternFish[0] ?: 0))
            lanternFish.clear()
            updates.forEach { (internalTimer, count) ->
                lanternFish[internalTimer] = (lanternFish[internalTimer] ?: 0) + count
            }
        }

        return lanternFish.values.sum()
    }

    private fun ageFish(age: Int, amount: Long): Pair<Int, Long> {
        return if (age == 0) (6 to amount) else (age - 1 to amount)
    }

    private companion object {
        const val INPUT_FILE = "/day6/input.txt"
        val input = Day6::class.java.getResourceAsStream(INPUT_FILE)!!.bufferedReader().readLines()
    }
}
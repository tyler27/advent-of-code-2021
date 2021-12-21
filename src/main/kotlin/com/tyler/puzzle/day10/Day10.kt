package com.tyler.puzzle.day10

import com.tyler.puzzle.Puzzle
import java.util.*


/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day10 : Puzzle<Long> {
    private val input = Day10::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()

    override fun solvePartOne(): Long = calculateScore(true)
    override fun solvePartTwo(): Long = calculateScore(false)

    private fun calculateScore(isPartOne: Boolean): Long {
        val chars = mapOf(
            '(' to 0,
            '[' to 1,
            '{' to 2,
            '<' to 3,
            ')' to 4,
            ']' to 5,
            '}' to 6,
            '>' to 7
        )

        val convert1 = intArrayOf(3, 57, 1197, 25137)

        var stack: Stack<Int>
        var answer: Long = 0
        val scores: MutableList<Long> = ArrayList()

        for (inp in input) {
            stack = Stack()
            var consider = true

            for (letter in inp) {
                val let = chars[letter]!!
                if (let < 4) {
                    stack.push(let)
                }
                when {
                    let >= 4 -> {
                        if (stack.pop() != let % 4) {
                            consider = false
                            answer += convert1[let % 4]
                            break
                        }
                    }
                }
            }
            if (!isPartOne && consider) {
                answer = 0
                while (!stack.isEmpty()) {
                    answer *= 5
                    answer += (stack.pop() + 1).toLong()
                }
                scores.add(answer)
            }
        }
        scores.sort()
        return if (isPartOne) answer else scores[scores.size / 2]
    }

    private companion object {
        const val INPUT_FILE = "/day10/input.txt"
    }
}

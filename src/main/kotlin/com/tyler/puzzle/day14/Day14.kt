package com.tyler.puzzle.day14

import com.tyler.puzzle.Puzzle
import com.tyler.puzzle.day13.Day13

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day14 : Puzzle<Long> {
    private val input = Day13::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()
        .joinToString("\n")

    private val answer = getAnswer()

    override fun solvePartOne(): Long {
        return answer.first
    }

    override fun solvePartTwo(): Long {
        return answer.second
    }

    private fun getAnswer(): Pair<Long, Long> {
        val (template, instructions) = input.split("\n\n")
        val polymer = template.windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        val instructionMapping = mapInstructions(instructions)
        val getResultAfter = step(getCurrentStep(instructionMapping))
        val calculate = calculate(template)
        val partOne = getResultAfter(polymer, 10)
        return calculate(partOne) to calculate(getResultAfter(partOne, 30))
    }

    private fun mapInstructions(instructions: String) = instructions.split("\n").map { line ->
        val split = line.split(" -> ")
        split.first() to split.last()
    }

    private fun calculate(template: String) = { values: Map<String, Long> ->
        val result = values.entries.fold(mutableMapOf<Char, Long>()) { cur, entry ->
            cur[entry.key.first()] = (cur[entry.key.first()] ?: 0L) + entry.value
            cur
        }.mapValues { (k, v) -> if (k == template.last()) v + 1 else v }

        result.maxOf(Map.Entry<Char, Long>::value) - result.minOf(Map.Entry<Char, Long>::value)
    }

    private fun getCurrentStep(mapping: List<Pair<String, String>>) =
        { current: Map<String, Long> ->
            val result = mutableMapOf<String, Long>()
            current.forEach { (part, count) ->
                mapping.find { (find, _) -> find == part }?.let { (_, replace) ->
                    result[part.first() + replace] = (result[part.first() + replace] ?: 0) + count
                    result[replace + part.last()] = (result[replace + part.last()] ?: 0) + count
                }
            }

            result
        }

    private fun step(step: (Map<String, Long>) -> MutableMap<String, Long>) =
        { start: Map<String, Long>, steps: Int -> (1..steps).fold(start) { cur, _ -> step(cur) } }

    private companion object {
        const val INPUT_FILE = "/day14/input.txt"
    }
}
package com.tyler.puzzle.day8

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day8 : Puzzle<Int> {
    private val input = Day8::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()

    private val inputSeqments = input.map { it.split(" | ").first().split(" ") }
    private val outputSegments = input.map { it.split(" | ").last().split(" ") }

    override fun solvePartOne(): Int =
        outputSegments.sumOf { it.count { it.length in intArrayOf(2, 3, 4, 7) } }

    override fun solvePartTwo(): Int =
        outputSegments
            .mapIndexed { index, outputSegment -> Pair(mapInputSegments(inputSeqments[index]), outputSegment) }
            .sumOf(this::getOutputValue)

    private fun getOutputValue(pair: Pair<List<String>, List<String>>): Int {
        val outputValue = pair.second.map { pair.first.indexOf(it.toCharArray().sorted().joinToString("")) }
        return outputValue.joinToString("").toInt()
    }

    private fun mapInputSegments(list: List<String>): List<String> {
        val segments = MutableList<String>(10) { "" }

        list.map { it.toCharArray().sorted().joinToString("") }.sortedBy(String::length).forEach { segment ->
            val intersectFirst = segment.toSet().intersect(segments[1].toSet())
            val intersectForth = segment.toSet().intersect(segments[4].toList())

            when (segment.length) {
                2 -> segments[1] = segment
                3 -> segments[7] = segment
                4 -> segments[4] = segment
                7 -> segments[8] = segment
                5 -> {
                    when {
                        intersectFirst.size == 2 -> segments[3] = segment
                        intersectForth.size == 3 -> segments[5] = segment
                        else -> segments[2] = segment
                    }
                }
                6 -> {
                    when {
                        intersectFirst.size < 2 -> segments[6] = segment
                        intersectForth.size == 4 -> segments[9] = segment
                        else -> segments[0] = segment
                    }
                }
            }
        }

        return segments
    }

    private companion object {
        const val INPUT_FILE = "/day8/input.txt"
    }
}
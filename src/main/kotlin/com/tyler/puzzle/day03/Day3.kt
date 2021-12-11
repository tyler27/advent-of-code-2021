package com.tyler.puzzle.day03

import com.tyler.puzzle.Puzzle


/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day3 : Puzzle<Long> {

    override fun solvePartOne(): Long {
        val gammaRate = input.first().indices.joinToString("") {
            val sum = sumOf(input, it)
            if (sum < 0) "0" else "1"
        }
        val epsilonRate = input.first().indices.joinToString("") {
            val sum = sumOf(input, it)
            if (sum > 0) "0" else "1"
        }
        return gammaRate.toLong(2) * epsilonRate.toLong(2)
    }

    override fun solvePartTwo(): Long {
        val oxygenGeneratorRating = ArrayList<String>(input)
        val co2ScrubberRating =  ArrayList<String>(input)

       input.first().indices.forEach { index ->
            val oxygenSum = sumOf(oxygenGeneratorRating, index)
            val co2Sum = sumOf(co2ScrubberRating, index)

           if (oxygenGeneratorRating.size == 1)
               return@forEach

           oxygenGeneratorRating.removeIf { it[index] == if (oxygenSum < 0) '1' else '0' }

           if (co2ScrubberRating.size == 1)
               return@forEach

           co2ScrubberRating.removeIf { it[index] == if (co2Sum < 0) '0' else '1' }
        }

        return (oxygenGeneratorRating[0].toLong(2) * co2ScrubberRating[0].toLong(2))
    }

    private fun sumOf(lines: List<String>, index: Int): Long = lines.sumOf { if (it[index] == '1') 1L else -1L }

    private companion object {
        const val INPUT_FILE = "/day3/input.txt"
        val input = Day3::class.java.getResourceAsStream(INPUT_FILE)!!.bufferedReader().readLines()
    }
}
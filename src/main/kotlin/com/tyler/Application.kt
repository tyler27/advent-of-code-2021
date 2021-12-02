package com.tyler

import com.tyler.puzzle.impl.DayOne
import com.tyler.puzzle.impl.DayTwo

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
fun main() {
    val dayOne = DayOne()
    println("Answer one: ${dayOne.solvePartOne()}")
    println("Answer two: ${dayOne.solvePartTwo()}")

    val dayTwo = DayTwo()
    println("Answer one: ${dayTwo.solvePartOne()}")
    println("Answer two: ${dayTwo.solvePartTwo()}")
}
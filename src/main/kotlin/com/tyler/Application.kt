package com.tyler

import com.tyler.puzzle.impl.Day1
import com.tyler.puzzle.impl.Day2
import com.tyler.puzzle.impl.Day3

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
fun main() {
    val dayOne = Day1()
    println("Answer one: ${dayOne.solvePartOne()}")
    println("Answer two: ${dayOne.solvePartTwo()}")

    val dayTwo = Day2()
    println("Answer one: ${dayTwo.solvePartOne()}")
    println("Answer two: ${dayTwo.solvePartTwo()}")

    val dayThree = Day3()
    println("Answer one: ${dayThree.solvePartOne()}")
    println("Answer two: ${dayThree.solvePartTwo()}")
}
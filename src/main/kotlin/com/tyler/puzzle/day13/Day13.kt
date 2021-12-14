package com.tyler.puzzle.day13

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day13 : Puzzle<Int> {
    private val input = Day13::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()

    override fun solvePartOne(): Int {
        return paper.crease(instructions.first()).size
    }

    override fun solvePartTwo(): Int {
        instructions.fold(paper) { paper, instruction -> paper.crease(instruction) }.printout()
        return 0
    }

    private val paper: Set<Coordinate> = parsePoints(input)
    private val instructions: List<Coordinate> = parseInstructions(input)

    private fun Set<Coordinate>.printout() {
        (0..this.maxOf(Coordinate::y)).forEach { y ->
            (0..this.maxOf(Coordinate::x)).forEach { x ->
                print(if (Coordinate(x, y) in this) "#" else " ")
            }
            println()
        }
    }

    private fun Set<Coordinate>.crease(instruction: Coordinate): Set<Coordinate> =
        if (instruction.x != 0) this.map { it.copy(x = it.x.creaseAt(instruction.x)) }.toSet()
        else this.map { it.copy(y = it.y.creaseAt(instruction.y)) }.toSet()

    private fun Int.creaseAt(crease: Int): Int =
        if (this < crease) this else (crease * 2) - this

    private fun parsePoints(input: List<String>): Set<Coordinate> =
        input.takeWhile(String::isNotEmpty)
            .map { it.split(",") }
            .map { Coordinate(it.first().toInt(), it.last().toInt()) }
            .toSet()

    private fun parseInstructions(input: List<String>): List<Coordinate> =
        input.takeLastWhile(String::isNotEmpty)
            .map { it.split("=") }
            .map {
                if (it.first().endsWith("y")) {
                    Coordinate(0, it.last().toInt())
                } else {
                    Coordinate(it.last().toInt(), 0)
                }
            }


    private companion object {
        const val INPUT_FILE = "/day13/input.txt"
    }
}
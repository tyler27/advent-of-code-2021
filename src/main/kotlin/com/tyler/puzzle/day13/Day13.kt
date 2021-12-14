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
        return coordinates.foldPaper(foldingPoints.first()).size
    }

    override fun solvePartTwo(): Int {
        foldingPoints.fold(coordinates) { paper, instruction -> paper.foldPaper(instruction) }.println()
        return 0
    }

    private val coordinates: Set<Coordinate> = parseCoordinates(input)
    private val foldingPoints: List<Coordinate> = generateFolds(input)

    private fun Set<Coordinate>.println() {
        (0..this.maxOf(Coordinate::y)).forEach { y ->
            (0..this.maxOf(Coordinate::x)).forEach { x ->
                print(if (Coordinate(x, y) in this) "#" else " ")
            }
            kotlin.io.println()
        }
    }

    private fun Set<Coordinate>.foldPaper(coordinate: Coordinate): Set<Coordinate> =
        if (coordinate.x != 0) this.map { it.copy(x = it.x.foldAt(coordinate.x)) }.toSet()
        else this.map { it.copy(y = it.y.foldAt(coordinate.y)) }.toSet()

    private fun Int.foldAt(position: Int): Int =
        if (this < position) this else (position * 2) - this

    private fun parseCoordinates(input: List<String>): Set<Coordinate> =
        input.takeWhile(String::isNotEmpty)
            .map { it.split(",") }
            .map { Coordinate(it.first().toInt(), it.last().toInt()) }
            .toSet()

    private fun generateFolds(input: List<String>): List<Coordinate> =
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
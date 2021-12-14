package com.tyler.puzzle.day04

import com.tyler.puzzle.Puzzle
import com.tyler.puzzle.day03.Day3


/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day4 : Puzzle<Int> {
    private val randomNumbers = input.readLine().split(",").map(String::toInt)
    private val boards = input.readLines()
        .asSequence()
        .drop(1)
        .filterNot(String::isBlank)
        .chunked(5) {
            Board(it.map { string -> string.trim().split("\\s+".toRegex()).map(String::toInt).toMutableList() })
        }
        .toList()

    override fun solvePartOne(): Int {
        randomNumbers.forEach { pickedNumber ->
            boards.forEach { board ->
                board.mark(pickedNumber)
                val boardsWon = getBoardsWon()
                if (boardsWon.isNotEmpty()) {
                    return boardsWon.first().sum() * pickedNumber
                }
            }
        }

        return -1
    }

    override fun solvePartTwo(): Int {
        val boardsWon = arrayListOf<Board>()

        randomNumbers.forEachIndexed { index, pickedNumber ->
            if (boardsWon.size != boards.size) {
                boards.forEach { it.mark(pickedNumber) }
                val winners = getBoardsWon().filterNot(boardsWon::contains)

                if (winners.isEmpty()) return@forEachIndexed

                boardsWon.addAll(winners)
            } else {
                return boardsWon.last().sum() * randomNumbers[index - 1]
            }
        }

        return -1
    }

    private fun getBoardsWon() = boards.filter(Board::hasWon).distinct()

    private companion object {
        const val INPUT_FILE = "/day4/input.txt"
        val input = Day3::class.java.getResourceAsStream(INPUT_FILE)!!.bufferedReader()
    }
}
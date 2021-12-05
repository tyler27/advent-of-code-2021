package com.tyler.puzzle.day4

@JvmInline
value class Board(
    private val boardNumbers: List<MutableList<Int>>
) {
    fun mark(pickedNumber: Int) = boardNumbers.forEach {
        it.forEachIndexed { index, number -> if (pickedNumber == number) it[index] = -1 }
    }
    private fun rowWon(): Boolean = boardNumbers.any { row -> row.all { it == -1 } }
    private fun colWon(): Boolean = with(0 until 5) { any { row -> all { col -> boardNumbers[col][row] == -1 } }}
    fun hasWon() = rowWon() || colWon()
    fun sum(): Int = boardNumbers.sumOf { row -> row.sumOf { if (it == -1) 0 else it } }
}
package com.tyler.puzzle.day21

class Player(var pos: Int, var score: Int) {
    fun hasWon() = score >= 1000
}

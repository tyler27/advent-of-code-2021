package com.tyler.puzzle.day21

import com.tyler.puzzle.Puzzle
import kotlin.math.max


/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day21 : Puzzle<Any> {
    private val deterministicDieGame = DeterministicDieGame(Player(P1_START - 1, 0), Player(Companion.P2_START - 1, 0))

    override fun solvePartOne() = deterministicDieGame.play()

    data class Universe(var pos1: Int, var pos2: Int, var score1: Int, var score2: Int)

    override fun solvePartTwo(): Any {
        val multiverse = mutableMapOf(Universe(5, 10, 0, 0) to 1L)

        var p1TotalWins = 0L
        var p2TotalWins = 0L

        while (multiverse.isNotEmpty()) {
            val iterator = multiverse.iterator()

            val (universe, result) = iterator.next()

            iterator.remove()

            val (p1, p2, scoreP1, scoreP2) = universe

            val pair = rollQuantumDie(p1, scoreP1, p1TotalWins, result, p2, scoreP2, p2TotalWins, multiverse)
            p1TotalWins = pair.first
            p2TotalWins = pair.second
        }

        return max(p1TotalWins, p2TotalWins)
    }

    private fun rollQuantumDie(
        p1: Int,
        scoreP1: Int,
        p1Wins: Long,
        result: Long,
        p2: Int,
        scoreP2: Int,
        p2Wins: Long,
        multiVerse: MutableMap<Universe, Long>
    ): Pair<Long, Long> {
        var p1TotalWins = p1Wins
        repeat(3) { d1 ->
            repeat(3) { d2 ->
                repeat(3) { d3 ->
                    val p1Pos = moveQuantumPlayer(p1, d1, d2, d3)

                    var p1Score = scoreP1

                    p1Score += p1Pos

                    if (p1Score >= 21) {
                        p1TotalWins += result
                    } else {
                        splitUniverse(p2, scoreP2, p2Wins, result, p1Pos, p1Score, multiVerse)
                    }
                }
            }
        }
        return Pair(p1TotalWins, p2Wins)
    }

    private fun splitUniverse(
        p2: Int,
        scoreP2: Int,
        p2Wins1: Long,
        result: Long,
        px1: Int,
        scorePx1: Int,
        universeState: MutableMap<Universe, Long>
    ) {
        var p2Wins11 = p2Wins1
        repeat(3) { dx1 ->
            repeat(3) { dx2 ->
                repeat(3) { dx3 ->
                    val newPos = moveQuantumPlayer(p2, dx1, dx2, dx3)

                    var p2Score = scoreP2 + moveQuantumPlayer(p2, dx1, dx2, dx3)
                    p2Score += newPos

                    if (p2Score >= 21) {
                        p2Wins11 += result
                    } else {
                        val state = Universe(px1, newPos, scorePx1, p2Score)
                        if (state in universeState) {
                            universeState[state] = result + universeState[state]!!
                        } else {
                            universeState[state] = result
                        }
                    }
                }
            }
        }
    }

    private fun moveQuantumPlayer(position1: Int, position2: Int, score1: Int, score2: Int): Int {
        return (((position1 + position2 + score1 + score2 + 3) - 1) % 10) + 1
    }

    private companion object {
        const val INPUT_FILE = "/day21/input.txt"
        private val input = Day21::class.java.getResourceAsStream(INPUT_FILE)!!
            .bufferedReader()
            .readLines()
        private val P1_START = input.first().split("Player 1 starting position: ").last().toInt()
        private val P2_START = input.last().split("Player 2 starting position: ").last().toInt()
    }
}

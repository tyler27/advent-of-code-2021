package com.tyler.puzzle.day21

import com.tyler.puzzle.Puzzle
import kotlin.math.max


/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day21 : Puzzle<Any> {
    private val deterministicDieGame = DeterministicDieGame(Player(P1_START - 1, 0), Player(P2_START - 1, 0))

    override fun solvePartOne() = deterministicDieGame.play()

    data class Universe(var pos1: Int, var pos2: Int, var score1: Int, var score2: Int)

    override fun solvePartTwo(): Any {
        val multiverse = mutableMapOf(Universe(P1_START, P2_START, 0, 0) to 1L)

        var p1TotalWins = 0L
        var p2TotalWins = 0L

        while (multiverse.isNotEmpty()) {
            val iterator  = multiverse.iterator()

            val (universe, result) = iterator .next()

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
        p1TotalWins: Long,
        result: Long,
        p2: Int,
        scoreP2: Int,
        p2TotalWins: Long,
        multiverse: MutableMap<Universe, Long>
    ): Pair<Long, Long> {
        var p1TotalWins1 = p1TotalWins
        repeat(3) { d1 ->
            repeat(3) { d2 ->
                repeat(3) { d3 ->
                    val newPos = moveQuantumPlayer(p1, d1, d2, d3)

                    var p1Score = scoreP1

                    p1Score += newPos
                    if (p1Score >= 21) {
                        p1TotalWins1 += result
                    } else {
                        splitUniverse(p2, scoreP2, p2TotalWins, result, newPos, p1Score, multiverse)
                    }
                }
            }
        }
        return Pair(p1TotalWins1, p2TotalWins)
    }

    private fun splitUniverse(
        p2: Int,
        scoreP2: Int,
        p2TotalWins1: Long,
        result: Long,
        px1: Int,
        scorePx1: Int,
        universeState: MutableMap<Universe, Long>
    ) {
        var p2TotalWins11 = p2TotalWins1
        repeat(3) { dx1 ->
            repeat(3) { dx2 ->
                repeat(3) { dx3 ->
                    val px2 = moveQuantumPlayer(p2, dx1, dx2, dx3)

                    var scorePx2 = scoreP2
                    scorePx2 += px2
                    if (scorePx2 >= 21) {
                        p2TotalWins11 += result
                    } else {
                        val state1 = Universe(px1, px2, scorePx1, scorePx2)
                        if (state1 in universeState) {
                            universeState[state1] = result + universeState[state1]!!
                        } else {
                            universeState[state1] = result
                        }
                    }
                }
            }
        }
    }

    private fun moveQuantumPlayer(position1: Int, position2: Int, d2: Int, d3: Int): Int {
        return (((position1 + position2 + d2 + d3 + 3) - 1) % 10) + 1
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

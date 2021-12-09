package com.tyler.puzzle.day9

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day9 : Puzzle<Int> {
    private val positions = Day9::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()
        .map { it.map(Character::getNumericValue) }

    override fun solvePartOne(): Int {
        var count = 0
        var lows = 0

        for (index in positions.indices) {
            for (position in positions[index].indices) {
                if (adjacent(index, position)) {
                    count++
                    lows += positions[index][position]
                }
            }
        }

        return count + lows
    }

    override fun solvePartTwo(): Int {
        val basins = mutableListOf<Int>()

        positions.indices.map { index ->
            positions[index].indices
                .filter { adjacent(it, index) }
                .mapTo(basins) { getBasin(index, it) }
        }

        return basins.sortedDescending().take(3).reduce { x, y -> x * y }
    }

    private fun getNeighbors(index: Int, position: Int): Array<Int> {
        val up = positions.getOrNull(index - 1)?.getOrNull(position) ?: -1
        val down = positions.getOrNull(index + 1)?.getOrNull(position) ?: -1
        val left = positions.getOrNull(index)?.getOrNull(position - 1) ?: -1
        val right = positions.getOrNull(index)?.getOrNull(position + 1) ?: -1
        return arrayOf(up, down, left, right)
    }

    private fun adjacent(index: Int, i: Int): Boolean {
        val point = positions[index][i]
        val (up, down, left, right) = getNeighbors(index, i)
        if (up in 0..point) return false
        if (down in 0..point) return false
        if (right in 0..point) return false
        if (left in 0..point) return false
        return true
    }

    private fun getBasin(index: Int, position: Int, map: MutableMap<Pair<Int, Int>, Boolean> = mutableMapOf()): Int {
        val (up, down, left, right) = getNeighbors(position, index)

        val neighboringDirections = mapOf(
            Pair(index, position - 1) to up,
            Pair(index, position + 1) to down,
            Pair(index + 1, position) to right,
            Pair(index - 1, position) to left
        )

        neighboringDirections.forEach {
            if (it.value in 0..8 && map[it.key] == null) {
                map[it.key] = true
                getBasin(it.key.first, it.key.second, map)
            }
        }


        return map.size
    }

    private companion object {
        const val INPUT_FILE = "/day9/input.txt"
    }
}
package com.tyler.puzzle.day15

import com.tyler.puzzle.Puzzle
import java.util.*
import kotlin.math.abs

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day15 : Puzzle<Int> {
    private val inputs = Day15::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()

    private enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private val isLocationValid: (row: Int, col: Int, rowSize: Int, colSize: Int) -> Boolean =
        { row, col, rowSize, colSize ->
            (row in 0 until rowSize) && (col in 0 until colSize)
        }

    private val direction: (row: Int, col: Int, direction: Direction) -> Pair<Int, Int> =
        { row, col, direction ->
            val pair = when (direction) {
                Direction.UP -> Pair(row - 1, col)
                Direction.DOWN -> Pair(row + 1, col)
                Direction.LEFT -> Pair(row, col - 1)
                Direction.RIGHT -> Pair(row, col + 1)
            }
            pair
        }


    override fun solvePartOne(): Int {
        val rowSize = inputs.size
        val colSize = inputs[0].length
        val matrix = Array(rowSize) { IntArray(colSize) }
        inputs.initMatrix(matrix = matrix)
        return dijkstra(Pair(0, 0), matrix)
    }


    private fun List<String>.initMatrix(matrix: Array<IntArray>) {
        val rowSize = size
        val colSize = this.first().length
        for (row in 0 until rowSize) {
            for (col in 0 until colSize) {
                matrix[row][col] = this[row][col].digitToInt()
            }
        }
    }

    private fun Array<IntArray>.findAdjacent(row: Int, col: Int): List<Pair<Int, Int>> {
        val adjacentList = mutableListOf<Pair<Int, Int>>()
        val rowSize = this.size
        val colSize = this.first().size
        for (direction in Direction.values()) {
            val adjacent = direction(row, col, direction)
            if (isLocationValid(adjacent.first, adjacent.second, rowSize, colSize)) {
                adjacentList.add(Pair(adjacent.first, adjacent.second))
            }
        }

        return adjacentList
    }

    private fun dijkstra(start: Pair<Int, Int>, matrix: Array<IntArray>): Int {
        val priorityQueue = PriorityQueue(DistanceComparator())
        val visited = hashSetOf<Pair<Int, Int>>()
        val totalRiskLevel = hashMapOf<Pair<Int, Int>, Int>()

        totalRiskLevel[start] = 0
        priorityQueue.add(RiskLocation(start, 0))

        while (priorityQueue.isNotEmpty()){
            val (location, riskLevel) = priorityQueue.remove()
            visited.add(location)

            if (totalRiskLevel.getOrDefault(location, Int.MAX_VALUE) < riskLevel) continue

            for(adj in matrix.findAdjacent(location.first,location.second)){
                if (visited.contains(adj)) continue
                val newRiskLevel = totalRiskLevel.getOrDefault(location, Int.MAX_VALUE) + matrix[adj.first][adj.second]
                if (newRiskLevel < totalRiskLevel.getOrDefault(adj, Int.MAX_VALUE)){
                    totalRiskLevel[adj] = newRiskLevel
                    priorityQueue.add(RiskLocation(adj,newRiskLevel))
                }
            }
        }

        return totalRiskLevel[Pair(matrix.size - 1, matrix.first().size - 1)]!!
    }

    private data class RiskLocation(
        val location: Pair<Int,Int>,
        val risk: Int
    )

    private class DistanceComparator : Comparator<RiskLocation> {
        override fun compare(first: RiskLocation, second: RiskLocation): Int {
            return first.risk.compareTo(second.risk)
        }
    }

    override fun solvePartTwo(): Int {
        val rowSize = inputs.size
        val colSize = inputs.first().length
        val matrix = Array(rowSize * 5) { IntArray(colSize * 5) }

        fun Int.nextLevel(): Int = if (this > 9) abs(this - 9) else this

        for (row in 0 until rowSize){
            for (col in 0 until colSize){
                var counter = 1
                matrix[row][col] = inputs[row][col].digitToInt()

                repeat(4){
                    val newCol = col + (colSize * counter)
                    val newRow = row + (rowSize * counter)
                    matrix[row][newCol] = (matrix[row][col] + it + 1).nextLevel()
                    matrix[newRow][col] = (matrix[row][col] + it + 1).nextLevel()

                    var newCounter = 1
                    repeat(4){ count ->
                        matrix[row + (rowSize * newCounter)][col + (colSize * counter)] = (matrix[row][newCol] + count + 1).nextLevel()
                        ++newCounter
                    }

                    ++counter
                }
            }
        }


        return dijkstra(Pair(0, 0), matrix)
    }

    private companion object {
        const val INPUT_FILE = "/day15/input.txt"
    }
}

package com.tyler.puzzle.day19

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day19 : Puzzle<Any> {
    private val input = Day19::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines().reduce { a, b -> "$a\n$b" }
    private val scanners: List<Set<Coordinate>> = parseInput(input)

    private fun parseInput(input: String): List<Set<Coordinate>> =
        input.split("\n\n").map { singleScanner ->
            singleScanner
                .lines()
                .drop(1)
                .map(Coordinate.Companion::of)
                .toSet()
        }

    private fun solve(): Solution {
        val baseSector = scanners.first().toMutableSet()
        val foundScanners = mutableSetOf(Coordinate(0,0,0))
        val unmappedSectors = ArrayDeque<Set<Coordinate>>().apply { addAll(scanners.drop(1)) }
        while(unmappedSectors.isNotEmpty()) {
            val thisSector = unmappedSectors.removeFirst()
            when (val transform = findTransformIfIntersects(baseSector, thisSector)) {
                null -> unmappedSectors.add(thisSector)
                else -> {
                    baseSector.addAll(transform.beacons)
                    foundScanners.add(transform.scanner)
                }
            }
        }
        return Solution(foundScanners, baseSector)
    }

    private fun findTransformIfIntersects(left: Set<Coordinate>, right: Set<Coordinate>): Transform? =
        (0 until 6).firstNotNullOfOrNull { face ->
            (0 until 4).firstNotNullOfOrNull { rotation ->
                val rightReoriented = right.map { it.move(face).rotate(rotation) }.toSet()
                left.firstNotNullOfOrNull { s1 ->
                    rightReoriented.firstNotNullOfOrNull { s2 ->
                        val difference = s1 - s2
                        val moved = rightReoriented.map { it + difference }.toSet()
                        if (moved.intersect(left).size >= 12) {
                            Transform(difference, moved)
                        } else null
                    }
                }
            }
        }


    private class Transform(val scanner: Coordinate, val beacons: Set<Coordinate>)
    private class Solution(val scanners: Set<Coordinate>, val beacons: Set<Coordinate>)

    private companion object {
        const val INPUT_FILE = "/day19/input.txt"
    }

    override fun solvePartOne(): Any {
        return solve().beacons.size
    }

    override fun solvePartTwo(): Any {
        return solve().scanners.pairs().maxOf { it.first distance it.second }
    }

    private fun <T> Collection<T>.pairs(): List<Pair<T,T>> =
        this.flatMapIndexed { index, a ->
            this.drop(index).map { b -> a to b }
        }
}

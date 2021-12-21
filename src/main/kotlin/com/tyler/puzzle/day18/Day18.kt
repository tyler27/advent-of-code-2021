package com.tyler.puzzle.day18

import com.tyler.puzzle.Puzzle
import kotlin.math.ceil
import kotlin.math.floor

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day18 : Puzzle<Any> {

    override fun solvePartOne(): Any {
        return input.map(SnailFish.Companion::of).reduce { a, b -> a + b }.magnitude()
    }

    override fun solvePartTwo(): Any {
        return input.mapIndexed { index, left ->
            input.drop(index + 1).map { right ->
                listOf(
                    SnailFish.of(left) to SnailFish.of(right),
                    SnailFish.of(right) to SnailFish.of(left)
                )
            }.flatten()
        }.flatten()
            .maxOf { (it.first + it.second).magnitude() }
    }

    data class PairNumberDepth(val depth: Int, val pair: PairNumber)

    sealed class SnailFish {
        var parent: SnailFish? = null
        abstract fun magnitude(): Int
        abstract fun split(): Boolean
        abstract fun regularsInOrder(): List<RegularFish>
        abstract fun pairsInOrderWithDepth(depth: Int = 0): List<PairNumberDepth>

        operator fun plus(other: SnailFish): SnailFish =
            PairNumber(this, other).apply { reduce() }

        fun reduce() {
            do {
                val didSomething = explode() || split()
            } while(didSomething)
        }

        private fun root(): SnailFish = if (parent == null) this else parent!!.root()

        private fun explode(): Boolean {
            val pairs = root().pairsInOrderWithDepth()
            val explodingPair = pairs.firstOrNull { it.depth == 4 }?.pair
            if (explodingPair != null) {
                val regulars = root().regularsInOrder()
                regulars.elementAtOrNull(regulars.indexOfFirst { it === explodingPair.left } - 1)
                    ?.addValue(explodingPair.left as RegularFish)
                regulars.elementAtOrNull(regulars.indexOfFirst { it === explodingPair.right } + 1)
                    ?.addValue(explodingPair.right as RegularFish)
                (explodingPair.parent as PairNumber).childHasExploded(explodingPair)
                return true
            }
            return false
        }

        companion object {
            fun of(input: String): SnailFish {
                val stack = mutableListOf<SnailFish>()
                input.forEach { char ->
                    when {
                        char.isDigit() -> stack.add(RegularFish(char.digitToInt()))
                        char == ']' -> {
                            val right = stack.removeLast()
                            val left = stack.removeLast()
                            stack.add(PairNumber(left, right))
                        }
                    }
                }
                return stack.removeFirst()
            }
        }
    }

    data class RegularFish(var value: Int) : SnailFish() {
        override fun magnitude(): Int = value
        override fun split(): Boolean = false
        override fun regularsInOrder(): List<RegularFish> = listOf(this)
        override fun pairsInOrderWithDepth(depth: Int): List<PairNumberDepth> = emptyList()

        fun addValue(amount: RegularFish) {
            this.value += amount.value
        }

        fun splitToPair(splitParent: SnailFish): PairNumber =
            PairNumber(
                RegularFish(floor(value.toDouble() / 2.0).toInt()),
                RegularFish(ceil(value.toDouble() / 2.0).toInt())
            ).apply { this.parent = splitParent }

        override fun toString(): String =
            value.toString()
    }

    data class PairNumber(var left: SnailFish, var right: SnailFish) : SnailFish() {
        init {
            left.parent = this
            right.parent = this
        }

        override fun magnitude(): Int = (left.magnitude() * 3) + (right.magnitude() * 2)

        fun childHasExploded(child: PairNumber) {
            val replacement = RegularFish(0).apply { parent = this@PairNumber.parent }
            when {
                left === child -> left = replacement
                else -> right = replacement
            }
        }

        override fun regularsInOrder(): List<RegularFish> =
            this.left.regularsInOrder() + this.right.regularsInOrder()

        override fun pairsInOrderWithDepth(depth: Int): List<PairNumberDepth> =
            this.left.pairsInOrderWithDepth(depth + 1) +
                    listOf(PairNumberDepth(depth, this)) +
                    this.right.pairsInOrderWithDepth(depth + 1)

        override fun split(): Boolean {
            if (left is RegularFish) {
                val actualLeft = left as RegularFish
                if (actualLeft.value >= 10) {
                    left = actualLeft.splitToPair(this)
                    return true
                }
            }
            if (left.split()) return true
            if (right is RegularFish) {
                val actualRight = right as RegularFish
                if (actualRight.value >= 10) {
                    right = actualRight.splitToPair(this)
                    return true
                }
            }
            return right.split()
        }

        override fun toString(): String =
            "[$left,$right]"
    }

    private val input = Day18::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()

    private companion object {
        const val INPUT_FILE = "/day18/input.txt"
    }

}

package com.tyler.puzzle.day19

import kotlin.math.absoluteValue

data class Coordinate(val x: Int, val y: Int, val z: Int) {

    infix fun distance(other: Coordinate): Int =
        (this.x - other.x).absoluteValue + (this.y - other.y).absoluteValue + (this.z - other.z).absoluteValue

    operator fun plus(other: Coordinate): Coordinate =
        Coordinate(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Coordinate): Coordinate =
        Coordinate(x - other.x, y - other.y, z - other.z)

    fun move(direction: Int): Coordinate =
        when (direction) {
            0 -> this
            1 -> Coordinate(x, -y, -z)
            2 -> Coordinate(x, -z, y)
            3 -> Coordinate(-y, -z, x)
            4 -> Coordinate(y, -z, -x)
            5 -> Coordinate(-x, -z, -y)
            else -> throw IllegalStateException("Invalid face direction")
        }

    fun rotate(rotating: Int): Coordinate =
        when (rotating) {
            0 -> this
            1 -> Coordinate(-y, x, z)
            2 -> Coordinate(-x, -y, z)
            3 -> Coordinate(y, -x, z)
            else -> throw IllegalStateException("Invalid rotation")
        }

    companion object {
        fun of(rawInput: String): Coordinate = rawInput.split(",").let { part ->
            Coordinate(part[0].toInt(), part[1].toInt(), part[2].toInt())
        }
    }
}

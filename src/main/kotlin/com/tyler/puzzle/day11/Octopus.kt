package com.tyler.puzzle.day11

class Octopus(val location: Pair<Int, Int>, var energy: Int) {
    var last = -1
    var counter = 0

    fun shouldFlash(step: Int) = step > last && energy > 9

    fun flash(step: Int): Boolean {
        if (!shouldFlash(step)) return false

        last = step
        energy = 0
        counter += 1
        return true
    }

    fun handleStep(step: Int) {
        if (step <= last) return

        energy += 1
    }
}
package com.tyler.puzzle.day21

class DeterministicDieGame(
    private val p1: Player,
    private val p2: Player,
) {
    var rolls: Int = 0

    private fun roll(): Int = rolls++ % 100 + 1

    fun play(): Int {
        while (true) {
            handleTurn(p1)
            if (p1.hasWon()) return p2.score * rolls
            handleTurn(p2)
            if (p2.hasWon()) return p1.score * rolls
        }
    }

    private fun handleTurn(player: Player) {
        val outcome = roll() + roll() + roll()
        player.pos = (player.pos + outcome) % 10
        player.score += player.pos + 1
    }
}

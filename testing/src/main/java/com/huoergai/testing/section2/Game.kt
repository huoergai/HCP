package com.huoergai.testing.section2

/**
 * D&T: 2020-06-26 23:30
 * Des:
 */
class Game {
    var score = 0
        private set
    var highScore = 0
        private set

    /**
     * the first red test
     */
    fun incrementScore0() {
        // increment score and high score when needed

    }

    /**
     * first green test refactored from previous red test
     */
    fun incrementScore1() {
        // increment score and high score when needed
        score++
        if (score > highScore) {
            highScore++
        }
    }


}
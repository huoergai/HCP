package com.huoergai.testing.section2.cocktail

/**
 * D&T: 2020-06-28 17:36
 * Des:
 */
class Score(highestScore: Int = 0) {
    var current = 0
        private set
    var highest = highestScore
        private set

    fun increment() {
        current++
        if (current > highest) {
            highest = current
        }
    }

}
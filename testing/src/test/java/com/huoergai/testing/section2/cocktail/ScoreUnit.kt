package com.huoergai.testing.section2.cocktail

import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * D&T: 2020-06-28 17:59
 * Des:
 */
class ScoreUnitTests {
    @Test
    fun whenIncrementingScore_shouldIncrementCurrentScore() {
        val score = Score()
        score.increment()
        assertEquals("Current score should have been 1", 1, score.current)
    }

    @Test
    fun whenIncrementingScore_aboveHighScore_shouldIncrementHighScore() {
        val score = Score()
        score.increment()
        Assertions.assertEquals(1, score.highest)
    }

    @Test
    fun whenIncrementingScore_belowHighScore_shouldNotIncrementHighScore() {
        val score = Score(10)
        score.increment()
        Assertions.assertEquals(10, score.highest)
    }
}
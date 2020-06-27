package com.huoergai.testing.section2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

/**
 * D&T: 2020-06-26 23:32
 * Des:
 */
internal class GameUnitTests {
    private lateinit var game: Game

    @BeforeEach
    fun initTest() {
        game = Game()
    }

    @Disabled("buf fixed in next refactored fun")
    @Test
    fun shouldIncrementHighScore_whenIncrementingScore() {
        game.incrementScore0()
        if (game.highScore == 1) {
            println("success")
        } else {
            fail("score and high score don't match")
        }
    }

    @Test
    fun shouldIncrementHighScore_whenIncrementingScoreRe() {
        game.incrementScore1()
        if (game.highScore == 1) {
            println("success")
        } else {
            fail("score and high score don't match")
        }
    }

    // ------------------------------ divider --------------------------------

    @Test
    fun whenIncrementingScore_shouldIncrementCurrentScore() {
        game.incrementScore1()
        assertEquals(1, game.score)
    }

    @Test
    fun whenIncrementingScore_aboveHighScore_shouldIncrementHighScore() {
        game.incrementScore1()
        assertEquals(1, game.highScore)
    }

    @Test
    fun whenIncrementingScore_belowHighScore_shouldNotIncrementHighScore() {
        game = Game(10)
        game.incrementScore2()
        assertEquals(10, game.highestScore)
    }



}
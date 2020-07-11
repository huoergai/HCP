package com.huoergai.testing.section2.cocktail

import com.huoergai.testing.section2.Game
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.verify

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
        if (game.highestScore == 1) {
            println("success")
        } else {
            fail("score and high score don't match")
        }
    }

    @Test
    fun shouldIncrementHighScore_whenIncrementingScoreRe() {
        game.incrementScore()
        if (game.highestScore == 1) {
            println("success")
        } else {
            fail("score and high score don't match")
        }
    }

    // ------------------------- chapter 7  -----------------------

    @Test
    fun whenAnswering_shouldDelegateToQuestion() {
        val question = Mockito.mock(Question::class.java)
        val gm =
            Game(questions = mutableListOf(question))
        gm.answer(question, "OPTION")
        // verify(question, times(1)).answer(eq("OPTION"))
        verify(question).answer(eq("OPTION"))
    }

    @Test
    fun whenAnsweringCorrectly_shouldIncrementCurrentScore() {
        val question = mock<Question>()
        whenever(question.answer(anyString())).thenReturn(true)
        val score = mock<Score>()
        val gm =
            Game(score, mutableListOf(question))

        // val gm = Game(questions = mutableListOf(question))
        gm.answer(question, "OPTION")
        // assertEquals(1, gm.currentScore)
        verify(score).increment()
    }


    @Test
    fun whenAnsweringIncorrectly_shouldNotIncrementCurrentScore() {
        val q = mock<Question>()
        whenever(q.answer(anyString())).thenReturn(false)
        val score = mock<Score>()
        val gm = Game(score, mutableListOf(q))
        // val gm = Game(questions = mutableListOf(q))
        gm.answer(q, "OPTION")
        // assertEquals(0, gm.currentScore)
        verify(score, never()).increment()
    }

}
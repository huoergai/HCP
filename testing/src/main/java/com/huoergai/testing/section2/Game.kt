package com.huoergai.testing.section2

import android.util.Log
import com.huoergai.testing.section2.cocktail.Question
import com.huoergai.testing.section2.cocktail.Score

/**
 * D&T: 2020-06-26 23:30
 * Des:
 */
class Game(
    val score: Score = Score(0),
    var questions: MutableList<Question> = arrayListOf()
) {
    val currentScore: Int
        get() = score.current

    val highestScore: Int
        get() = score.highest

    private var questionIndex = -1

    /**
     * the first red test
     */
    fun incrementScore0() {
        // increment score and high score when needed
    }

    /**
     * first green test refactored from previous red test
     */
    fun incrementScore() {
        score.increment()
    }

    // --------------------- challenge --------------------

    fun nextQuestion(): Question? {
        if (questionIndex + 1 < questions.size) {
            questionIndex++
            return questions[questionIndex]
        }
        return null
    }

    fun answer(question: Question, option: String) {
        val ret = question.answer(option)
        if (ret) {
            Log.d("Game", "correct!")
            score.increment()
        } else {
            Log.d("Game", "incorrect!")
        }
        questions.add(++questionIndex, question)
    }

}
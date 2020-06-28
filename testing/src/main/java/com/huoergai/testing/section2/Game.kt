package com.huoergai.testing.section2

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
    // private val score = Score(highest)

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
    fun incrementScore1() {
        // increment score and high score when needed
        //  currentScore++
        score.increment()
        //  if (currentScore > highScore) {
        //      highScore++
        //  }
    }


    fun incrementScore2() {
        // currentScore++
        score.increment()
        // if (currentScore > highest) {
        //     highest = currentScore
        // }
    }

    // --------------------- challenge --------------------

    fun nextQuestion(): Question? {
        if (questionIndex + 1 < questions.size) {
            questionIndex++
            return questions[questionIndex]
        }
        // return questions.getOrNull(0)
        return null
    }

    fun answer(question: Question, option: String) {
        val ret = question.answer(option)
        if (ret) {
            // incrementScore2()
            score.increment()
        }
        questions.add(++questionIndex, question)
    }

}
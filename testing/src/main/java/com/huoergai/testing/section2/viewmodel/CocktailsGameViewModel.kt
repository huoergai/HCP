package com.huoergai.testing.section2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huoergai.testing.section2.Game
import com.huoergai.testing.section2.cocktail.CocktailsGameFactory
import com.huoergai.testing.section2.cocktail.CocktailsRepository
import com.huoergai.testing.section2.cocktail.Question
import com.huoergai.testing.section2.cocktail.Score

/**
 * D&T: 2020-06-29 12:10
 * Des:
 */
class CocktailsGameViewModel(
    val repo: CocktailsRepository,
    val factory: CocktailsGameFactory
) : ViewModel() {
    private val loadingLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Boolean>()
    private val questionLiveData = MutableLiveData<Question>()
    private val scoreLiveData = MutableLiveData<Score>()

    private var game: Game? = null

    fun getLoading(): LiveData<Boolean> = loadingLiveData
    fun getError(): LiveData<Boolean> = errorLiveData
    fun getQuestion(): LiveData<Question> = questionLiveData
    fun getScore(): LiveData<Score> = scoreLiveData

    fun initGame() {
        loadingLiveData.value = true
        errorLiveData.value = false
        factory.buildGame(object : CocktailsGameFactory.Callback {
            override fun onSuccess(game: Game) {
                loadingLiveData.value = false
                errorLiveData.value = false
                scoreLiveData.value = game.score
                questionLiveData.value = game.nextQuestion()
                this@CocktailsGameViewModel.game = game
                nextQuestion()
            }

            override fun onError() {
                loadingLiveData.value = false
                errorLiveData.value = true
            }

        })
    }

    fun nextQuestion() {
        game?.let {
            questionLiveData.value = it.nextQuestion()
        }
    }

    fun answerQuestion(q: Question, option: String) {
        game?.let {
            it.answer(q, option)
            repo.saveHighScore(it.score.highest)
            scoreLiveData.value = it.score
            questionLiveData.value = q
        }

    }
}
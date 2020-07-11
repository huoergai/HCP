package com.huoergai.testing.section2.cocktail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huoergai.testing.section2.Game

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
    private val cocktails = mutableListOf<Cocktail>(
        Cocktail("1", "Drink1", "image1"),
        Cocktail("2", "Drink2", "image2"),
        Cocktail("3", "Drink3", "image3"),
        Cocktail("4", "Drink4", "image4")
    )

    fun getLoading(): LiveData<Boolean> = loadingLiveData
    fun getError(): LiveData<Boolean> = errorLiveData
    fun getQuestion(): LiveData<Question> = questionLiveData
    fun getScore(): LiveData<Score> = scoreLiveData

    fun initGame() {
        loadingLiveData.value = true
        errorLiveData.value = false

        val score = Score()
        val qs = mutableListOf<Question>()
        cocktails.forEach {
            qs.add(Question(it.index, it.drink, it.drinkThumb))
        }
        game = Game(score, qs)
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
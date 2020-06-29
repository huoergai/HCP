package com.huoergai.testing.section2.cocktail

import com.huoergai.testing.section2.Game

/**
 * D&T: 2020-06-28 22:38
 * Des:
 */
class CocktailsGameFactoryImpl(private val repo: CocktailsRepository) : CocktailsGameFactory {


    override fun buildGame(callback: CocktailsGameFactory.Callback) {

        repo.getAlcoholic(object : RepositoryCallback<List<Cocktail>, String> {

            override fun onSuccess(cocktails: List<Cocktail>) {
                val questions = buildQuestions(cocktails)
                val socre = Score(repo.getHighScore())
                callback.onSuccess(Game(socre, questions))
            }

            override fun onError(e: String) {
                callback.onError()
            }

        })
    }

    private fun buildQuestions(cocktails: List<Cocktail>): MutableList<Question> {
        return cocktails.map { cocktail ->
            val otherCocktial = cocktails.shuffled().first {
                it != cocktail
            }
            Question(cocktail.drink, otherCocktial.drink, cocktail.drinkThumb)
        }.toMutableList()
    }
}
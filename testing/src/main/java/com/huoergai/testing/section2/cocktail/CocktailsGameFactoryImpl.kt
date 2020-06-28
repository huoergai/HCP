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
                callback.onSuccess(Game())
            }

            override fun onError(e: String) {
                callback.onError()
            }

        })
    }
}
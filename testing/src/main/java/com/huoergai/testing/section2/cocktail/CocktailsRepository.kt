package com.huoergai.testing.section2.cocktail

/**
 * D&T: 2020-06-28 20:33
 * Des:
 */
interface CocktailsRepository {

    fun getHighScore(): Int

    fun saveHighScore(score: Int)

    fun getAlcoholic(callback: RepositoryCallback<List<Cocktail>, String>)

}

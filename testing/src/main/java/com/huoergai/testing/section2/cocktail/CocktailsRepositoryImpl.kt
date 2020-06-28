package com.huoergai.testing.section2.cocktail

import android.content.SharedPreferences

/**
 * D&T: 2020-06-28 20:33
 * Des:
 */

private const val HIGH_SCORE_KEY = "HIGH_SCORE_KEY"

class CocktailsRepositoryImpl(
    private val api: CocktailsApi,
    private val sp: SharedPreferences
) : CocktailsRepository {

    override fun getHighScore(): Int = sp.getInt(HIGH_SCORE_KEY, 0)

    override fun saveHighScore(score: Int) {
        val highScore = getHighScore()
        if (score > highScore) {
            val editor = sp.edit()
            editor.putInt(HIGH_SCORE_KEY, score)
            editor.apply()
        }
    }

    override fun getAlcoholic(callback: RepositoryCallback<List<Cocktail>, String>) {

    }

}
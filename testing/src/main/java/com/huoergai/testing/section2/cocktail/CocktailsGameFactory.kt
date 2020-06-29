package com.huoergai.testing.section2.cocktail

import com.huoergai.testing.section2.Game

/**
 * D&T: 2020-06-28 22:36
 * Des:
 */
interface CocktailsGameFactory {

    fun buildGame(callback: Callback)

    interface Callback {
        fun onSuccess(game: Game)

        fun onError()
    }
}
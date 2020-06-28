package com.huoergai.testing.section2

import com.huoergai.testing.section2.cocktail.*
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * D&T: 2020-06-28 22:31
 * Des:
 */
class CocktailsGameFactoryUnitTests {
    private lateinit var repo: CocktailsRepository
    private lateinit var factory: CocktailsGameFactory

    @BeforeEach
    fun setup() {
        repo = mock()
        factory = CocktailsGameFactoryImpl(repo)
    }

    @Test
    fun buildGame_shouldGetCocktailsFromRepo() {
        factory.buildGame(mock())
        verify(repo).getAlcoholic(any())
    }

    @Test
    fun buildGame_shouldCallOnSuccess() {
        val callback = mock<CocktailsGameFactory.Callback>()
        setupRepositoryWithCocktails(repo)
        factory.buildGame(callback)
        verify(callback).onSuccess(any())
    }

    private val cocktails = listOf(Cocktail("1", "Drink1", "image1"))

    private fun setupRepositoryWithCocktails(repo: CocktailsRepository) {
        doAnswer {
            val callback: RepositoryCallback<List<Cocktail>, String> = it.arguments[0]
            callback.onSuccess(cocktails)
        }.whenever(repo).getAlcoholic(any())
    }


}
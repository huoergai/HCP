package com.huoergai.testing.section2

import com.huoergai.testing.section2.cocktail.*
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions.*
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

    private val cocktails = mutableListOf<Cocktail>(
        Cocktail("1", "Drink1", "image1"),
        Cocktail("2", "Drink2", "image2"),
        Cocktail("3", "Drink3", "image3"),
        Cocktail("4", "Drink4", "image4")
    )

    private fun setupRepositoryWithCocktails(repo: CocktailsRepository) {
        doAnswer {
            val callback: RepositoryCallback<List<Cocktail>, String> = it.getArgument(0)
            callback.onSuccess(cocktails)
        }.whenever(repo).getAlcoholic(any())
    }

    @Test
    fun buildGame_shouldCallOnError() {
        val cb = mock<CocktailsGameFactory.Callback>()
        setupRepositoryWithError(repo)
        factory.buildGame(cb)
        verify(cb).onError()
    }

    private fun setupRepositoryWithError(repo: CocktailsRepository) {
        doAnswer {
            val callback = it.getArgument<RepositoryCallback<List<Cocktail>, String>>(0)
            callback.onError("error")
        }.whenever(repo).getAlcoholic(any())
    }

    @Test
    fun buildGame_shouldGetHighScoreFromRepo() {
        setupRepositoryWithCocktails(repo)
        factory.buildGame(mock())
        verify(repo).getHighScore()
    }

    @Test
    fun buildGame_shouldBuildGameWithHighScore() {
        setupRepositoryWithCocktails(repo)
        val highScore = 100
        whenever(repo.getHighScore()).thenReturn(highScore)
        factory.buildGame(object : CocktailsGameFactory.Callback {

            override fun onSuccess(game: Game) = assertEquals(highScore, game.highestScore)

            override fun onError() = fail<Unit>()

        })
    }

    @Test
    fun buildGame_shouldBuildGameWithQuestions() {
        setupRepositoryWithCocktails(repo)
        factory.buildGame(object : CocktailsGameFactory.Callback {
            override fun onSuccess(game: Game) {
                cocktails.forEach {
                    assertQuestion(game.nextQuestion(), it.drink, it.drinkThumb)
                }
            }

            override fun onError() = fail<Unit>()
        })
    }

    fun assertQuestion(question: Question?, correctOption: String, imageUrl: String) {
        assertNotNull(question)
        assertEquals(imageUrl, question?.imageUrl)
        assertEquals(correctOption, question?.correctOption)
        assertNotEquals(correctOption, question?.incorrectOption)
    }
}
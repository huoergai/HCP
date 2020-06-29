package com.huoergai.testing.section2.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.huoergai.testing.section2.Game
import com.huoergai.testing.section2.cocktail.CocktailsGameFactory
import com.huoergai.testing.section2.cocktail.CocktailsRepository
import com.huoergai.testing.section2.cocktail.Question
import com.huoergai.testing.section2.cocktail.Score
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * D&T: 2020-06-29 11:49
 * Des:
 */
class CocktailsGameViewModelUnitTests {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repo: CocktailsRepository
    private lateinit var factory: CocktailsGameFactory
    private lateinit var viewModel: CocktailsGameViewModel
    private lateinit var loadingObserver: Observer<Boolean>
    private lateinit var errorObserver: Observer<Boolean>
    private lateinit var scoreOberver: Observer<Score>
    private lateinit var questionObserver: Observer<Question>
    private lateinit var game: Game

    @Before
    fun setup() {
        repo = mock()
        factory = mock()
        viewModel = CocktailsGameViewModel(repo, factory)

        game = mock()

        loadingObserver = mock()
        errorObserver = mock()
        scoreOberver = mock()
        questionObserver = mock()
        viewModel.getLoading().observeForever(loadingObserver)
        viewModel.getScore().observeForever(scoreOberver)
        viewModel.getQuestion().observeForever(questionObserver)
        viewModel.getError().observeForever(errorObserver)
    }

    private fun setupFactoryWithSuccessGame(game: Game) {
        doAnswer {
            val callback = it.getArgument<CocktailsGameFactory.Callback>(0)
            callback.onSuccess(game)
        }.whenever(factory).buildGame(any())
    }

    private fun setupFactoryWithError() {
        doAnswer {
            val cb = it.getArgument<CocktailsGameFactory.Callback>(0)
            cb.onError()
        }.whenever(factory).buildGame(any())
    }

    @Test
    fun init_shouldBuildGame() {
        viewModel.initGame()
        verify(factory).buildGame(any())
    }

    @Test
    fun init_shouldShowLoading() {
        viewModel.initGame()
        verify(loadingObserver).onChanged(eq(true))
    }

    @Test
    fun init_shouldHideError() {
        viewModel.initGame()
        verify(errorObserver).onChanged(eq(false))
    }

    @Test
    fun init_shouldShowError_whenFactoryReturnsError() {
        setupFactoryWithError()
        viewModel.initGame()
        verify(errorObserver).onChanged(eq(true))
    }

    @Test
    fun init_shouldHideLoading_whenFactoryReturnsError() {
        setupFactoryWithError()
        viewModel.initGame()
        verify(loadingObserver).onChanged(eq(false))
    }

    @Test
    fun init_shouldHideError_whenFactoryReturnsSuccess() {
        setupFactoryWithSuccessGame(game)
        viewModel.initGame()
        verify(errorObserver, times(2)).onChanged(eq(false))
    }

    @Test
    fun init_shouldHideLoading_whenFactoryReturnsSuccess() {
        setupFactoryWithSuccessGame(game)
        viewModel.initGame()
        verify(loadingObserver).onChanged(eq(false))
    }

    @Test
    fun init_shouldShowFirstQuestion_whenFactoryReturnsSuccess() {
        val q = mock<Question>()
        whenever(game.nextQuestion()).thenReturn(q)
        setupFactoryWithSuccessGame(game)
        viewModel.initGame()
        verify(questionObserver).onChanged(eq(q))
    }

    @Test
    fun nextQuestion_shouldShowQuestion() {
        val q1 = mock<Question>()
        val q2 = mock<Question>()
        whenever(game.nextQuestion()).thenReturn(q1).thenReturn(q2)
        setupFactoryWithSuccessGame(game)
        viewModel.initGame()
        viewModel.nextQuestion()
        verify(questionObserver).onChanged(eq(q2))
    }

    @Test
    fun answerQuestion_shouldDelegateToGame_saveHighScore_showQuestionAndScore() {
        val score = mock<Score>()
        val q = mock<Question>()
        whenever(game.score).thenReturn(score)
        setupFactoryWithSuccessGame(game)
        viewModel.initGame()

        viewModel.answerQuestion(q, "VALUE")
        inOrder(game, repo, questionObserver, scoreOberver) {
            verify(game).answer(eq(q), eq("VALUE"))
            verify(repo).saveHighScore(any())
            verify(scoreOberver).onChanged(eq(score))
            verify(questionObserver).onChanged(eq(q))
        }
    }

}
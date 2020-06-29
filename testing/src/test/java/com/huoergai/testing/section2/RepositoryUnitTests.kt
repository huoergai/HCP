package com.huoergai.testing.section2

import android.content.SharedPreferences
import com.huoergai.testing.section2.cocktail.CocktailsApi
import com.huoergai.testing.section2.cocktail.CocktailsRepository
import com.huoergai.testing.section2.cocktail.CocktailsRepositoryImpl
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * D&T: 2020-06-28 20:23
 * Des:
 */
@RunWith(MockitoJUnitRunner::class)
class RepositoryUnitTests {

    private lateinit var repository: CocktailsRepository

    @Mock
    private lateinit var api: CocktailsApi

    @Mock
    private lateinit var sp: SharedPreferences

    @Mock
    private lateinit var spEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        // api = mock()
        // spEditor = mock()
        // sp = mock()
        whenever(sp.edit()).thenReturn(spEditor)
        repository = CocktailsRepositoryImpl(api, sp)
    }

    @Test
    fun saveScore_shouldSaveToSharedPreferences() {
        val score = 10
        repository.saveHighScore(score)
        inOrder(spEditor) {
            verify(spEditor).putInt(any(), eq(score))
            verify(spEditor).apply()
        }
    }

    @Test
    fun getScore_shouldGetFromSharedPreferences() {
        repository.getHighScore()
        verify(sp).getInt(any(), any())
    }

    @Test
    fun saveScore_shouldNotSaveToSharedPreferencesIfLower() {
        val preSavedHighScore = 100
        val newHighScore = 10
        val spyRepo = spy(repository)
        doReturn(preSavedHighScore).whenever(spyRepo).getHighScore()

        spyRepo.saveHighScore(newHighScore)
        verify(spEditor, never()).putInt(any(), eq(newHighScore))
    }


}
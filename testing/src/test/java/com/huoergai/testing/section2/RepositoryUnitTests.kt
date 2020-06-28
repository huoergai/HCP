package com.huoergai.testing.section2

import android.content.SharedPreferences
import com.huoergai.testing.section2.cocktail.CocktailsApi
import com.huoergai.testing.section2.cocktail.CocktailsRepository
import com.huoergai.testing.section2.cocktail.CocktailsRepositoryImpl
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * D&T: 2020-06-28 20:23
 * Des:
 */
class RepositoryUnitTests {

    private lateinit var repository: CocktailsRepository
    private lateinit var api: CocktailsApi
    private lateinit var sp: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    @BeforeEach
    fun setup() {
        api = mock()
        spEditor = mock()
        sp = mock()
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
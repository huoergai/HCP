package com.huoergai.testing.section2.cocktail

interface RepositoryCallback<E, T> {
    fun onSuccess(cocktails: E)
    fun onError(e: String)
}
package com.huoergai.md.transition

import retrofit2.Call
import retrofit2.http.GET

/**
 * D&T: 2020-06-21 17:59
 * Des:
 */
interface DisneyService {
    @GET("DisneyPosters.json")
    fun fetchDisneyPosters(): Call<List<Poster>>
}
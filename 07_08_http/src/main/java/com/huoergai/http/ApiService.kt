package com.huoergai.http

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/user/query/{userId}")
    fun getUser(@Path("userId") userId: String): Call<User>
}
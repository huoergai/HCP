package com.huoergai.hcp.http

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huoergai.hcp.R
import com.huoergai.hcp.http.retrofit.ApiService
import retrofit2.Retrofit

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val retrofit = Retrofit.Builder().baseUrl("").build()
        val apiService = retrofit.create(ApiService::class.java)
        val userCall = apiService.getUser("id")
        val response = userCall.execute()

    }


}
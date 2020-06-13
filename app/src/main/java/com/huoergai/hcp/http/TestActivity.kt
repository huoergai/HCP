package com.huoergai.hcp.http

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huoergai.hcp.R
import com.huoergai.hcp.http.retrofit.ApiService
import okhttp3.*
import retrofit2.Retrofit
import java.io.IOException

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/users/huoergai/repos")
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        val userCall = apiService.getUser("id")
        val response = userCall.execute()


        val cp = CertificatePinner.Builder()
            .add("api.github.com", "sha256/lkjdfo2j3lsadj")
            .build()

        val client = OkHttpClient.Builder()
            .certificatePinner(cp)
            .build()

        val request = Request.Builder().url("").build()
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    println("response status code: " + response.code)
                }
            })

    }

}
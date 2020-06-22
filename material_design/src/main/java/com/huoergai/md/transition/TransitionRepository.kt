package com.huoergai.md.transition

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.huoergai.md.MDApp
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * D&T: 2020-06-21 18:34
 * Des:
 */
class TransitionRepository : Respository {
    override var isLoading: ObservableBoolean = ObservableBoolean(false)

    suspend fun loadDisneyPosters() {
        val posters = MDApp.getDB().posterDao().getPosters()
        val liveData = MutableLiveData<List<Poster>>()
        if (posters.isNotEmpty()) {
            liveData.postValue(posters)
            return
        }

        val okClient = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .client(okClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://gist.githubusercontent.com/skydoves/aa3bbbf495b0fa91db8a9e89f34e4873/raw/a1a13d37027e8920412da5f00f6a89c5a3dbfb9a/")
            .build()
        val disneyService = retrofit.create(DisneyService::class.java)
        disneyService.fetchDisneyPosters().enqueue(object : Callback<List<Poster>> {
            override fun onFailure(call: Call<List<Poster>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<Poster>>,
                response: Response<List<Poster>>
            ) {
                liveData.postValue(response.body())
            }

        })
    }

}
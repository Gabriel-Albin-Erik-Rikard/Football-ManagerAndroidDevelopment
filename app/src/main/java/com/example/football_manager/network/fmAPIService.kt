package com.example.football_manager.network

import com.example.football_manager.model.News
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface fmAPIService {
    @GET("news.json")
    suspend fun getNews(): List<News>

    companion object {
        var apiService: fmAPIService? = null
        fun getInstance(): fmAPIService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("localhost:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(apiService!!::class.java)
            }
            return apiService!!
        }
    }

}
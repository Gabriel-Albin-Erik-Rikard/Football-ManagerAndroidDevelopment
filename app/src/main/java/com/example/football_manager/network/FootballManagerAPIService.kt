package com.example.football_manager.network

import com.example.football_manager.model.News
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FootballManagerAPIService {
    @GET("person/{id}/news")
    suspend fun getPersonNews(@Path("id") id: Int): List<News>

    companion object {
        var apiService: FootballManagerAPIService? = null
        fun getInstance(): FootballManagerAPIService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(FootballManagerAPIService::class.java)
            }
            return apiService!!
        }
    }

}
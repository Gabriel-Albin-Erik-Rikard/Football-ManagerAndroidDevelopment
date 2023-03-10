package com.example.football_manager.network

import com.example.football_manager.model.News
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface fmAPIService {
    @GET("person/{id}/news")
    suspend fun getNews(@Path("id") id: Int): List<News>

    companion object {
        var apiService: fmAPIService? = null
        fun getInstance(): fmAPIService {
            println("Hello api")
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(fmAPIService::class.java)
            }
            return apiService!!
        }
    }

}
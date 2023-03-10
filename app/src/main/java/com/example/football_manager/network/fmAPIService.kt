package com.example.football_manager.network

import com.example.football_manager.model.News
import retrofit2.http.GET

interface fmAPIService {
    @GET("news.json")
    suspend fun getNews(): List<News>

}
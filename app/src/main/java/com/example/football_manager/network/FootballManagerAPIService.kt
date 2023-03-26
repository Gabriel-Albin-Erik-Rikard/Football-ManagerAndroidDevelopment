package com.example.football_manager.network

import com.example.football_manager.model.AuthPerson
import com.example.football_manager.model.News
import com.example.football_manager.model.Person
import com.example.football_manager.model.PersonTeams
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface FootballManagerAPIService {
    //NEWS Routes
    @GET("person/{id}/news")
    suspend fun getPersonNews(@Path("id") id: Int): List<News>

    @GET("news/{id}")
    suspend fun getSpecificNews(@Path("id") id: Int): News
/*
    @POST("news")
    suspend fun addNews(
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("writer") writer: String,
        writer1: String
    ): News
    @PUT ("news/{id}")
    suspend fun updateNews(@Path("id") id: Int, @Query("title") title: String, @Query("content") content: String): News

    @DELETE("news/{id}")
    suspend fun deleteNews(@Path("id") id: Int)
    */

    //NEWS Routes END

    // Get details of a person
    @GET("person/{id}")
    suspend fun getPerson(@Path("id") id: Int): Person

    // Groups
    // Gets all teams for a person
    @GET("team/{id}")
    suspend fun getPersonTeams(@Path("id") id: Int): PersonTeams

    // Login with email and password
    // with query parameters email and password
    @GET("/auth/login")
    suspend fun loginWithEmailAndPassword(@Query("email") email: String, @Query("password") password: String): AuthPerson


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
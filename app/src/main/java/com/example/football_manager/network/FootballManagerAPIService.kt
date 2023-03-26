package com.example.football_manager.network

import com.example.football_manager.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FootballManagerAPIService {
    @GET("person/{id}/news")
    suspend fun getPersonNews(@Path("id") id: Int): List<News>

    // Get details of a person
    @GET("person/{id}")
    suspend fun getPerson(@Path("id") id: Int): Person

    @GET("person/{id}/activites")
    suspend fun getPersonActivities(@Path("id") id: Int): List<Activities>

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
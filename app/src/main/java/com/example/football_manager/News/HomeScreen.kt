package com.example.football_manager.News

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.football_manager.MainActivity
import com.example.football_manager.viewmodel.NewsViewModel


const val NEWS_TITLE_MIN_LENGTH = 3
const val NEWS_TITLE_MAX_LENGTH = 30
const val NEWS_WRITER_NAME_MIN_LENGTH = 4
const val NEWS_WRITER_NAME_MAX_LENGTH = 10
const val NEWS_CONTENT_MIN_LENGTH = 10


@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    val newsViewModel = NewsViewModel()
    val sharedPreferences: SharedPreferences = MainActivity.context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)!!
    val id = sharedPreferences.getInt("id", 0)

    NavHost(
        navController = navController,
        startDestination = "viewAllNews"
    ) {
        composable("viewAllNews") {
            newsViewModel.getPersonNews(id)
            ViewAllNewsScreen(navController = navController, news = newsViewModel.newsList)
        }
        composable("viewOneNews/{id}") {
            val id = it.arguments!!.getString("id")!!.toInt()
            val singleNews = newsViewModel.newsList.find{it.id == id}
            ViewOneNewsScreen(singleNews!!, navController)
        }

       /* composable("viewOneEditedNews/{id}"){
            val id  = it.arguments!!.getString("id")!!.toInt()
            val singleNews = newsViewModel.newsList.find{it.id == id}
            EditNews(singleNews!! , navController )

        }*/
        /*
        composable("createNews") {
            CreateNews(navController =  navController)
        }

         */
    }
}














package com.example.football_manager.News

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.football_manager.model.News
import java.time.LocalDateTime


val now = LocalDateTime.now()
val newsRepository = NewsRepository().apply {
    addNews(
        "Welcome to Ekhagen",
        "In ekhagen you can find........",
        date = now.toString(),
        "Gabriel Adward"
    )
    addNews(
        "Training cancelled",
        "No Training Today because of ........",
        date = now.toString(),
        "Gabriel Adward"
    )
    addNews(
        "Squad For Upcoming Games",
        "Following players will start........",
        date = now.toString(),
        "Gabriel Adward"
    )
    addNews(
        "Game Cancelled",
        "Yesterdays game was cancelled........",
        date = now.toString(),
        "Gabriel Adward"
    )
}

class NewsRepository {
    private val multipleNews = mutableListOf<News>()

    fun addNews(title: String, content: String, date: String, writer: String): Int {
        val id = when {
            multipleNews.isEmpty() -> 1
            else -> multipleNews.last().id + 1
        }

        multipleNews.add(
            News(
                id,
                title,
                content,
                date,
                writer
            )
        )
        return id
    }

    fun getAllNews() = multipleNews

    fun getNewsById(id: Int) = multipleNews.find {
        it.id == id
    }

    fun deleteNewsById(id: Int) = multipleNews.remove(multipleNews.find {
        println(it.id)
        it.id == id

    })

    fun updateNewsById(id: Int, newTitle: String, newContent: String) {
        getNewsById(id)?.run {
            title = newTitle
            content = newContent
        }
    }

}

const val NEWS_TITLE_MIN_LENGTH = 3
const val NEWS_TITLE_MAX_LENGTH = 30
const val NEWS_WRITER_NAME_MIN_LENGTH = 4
const val NEWS_WRITER_NAME_MAX_LENGTH = 10
const val NEWS_CONTENT_MIN_LENGTH = 10


@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "viewAllNews"
    ) {
        composable("viewAllNews") {
            ViewAllNewsScreen(navController, newsRepository.getAllNews())
        }
        composable("viewOneNews/{id}") {
            val id = it.arguments!!.getString("id")!!.toInt()
            ViewOneNewsScreen(id, navController)
        }

        composable("viewOneEditedNews/{id}"){
            val id  = it.arguments!!.getString("id")!!.toInt()
            EditNews(id, navController)

        }
        composable("createNews") {
            CreateNews(navController = navController)
        }
    }
}














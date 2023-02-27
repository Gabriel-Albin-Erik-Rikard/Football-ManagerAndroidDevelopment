package com.example.football_manager

import java.time.LocalDateTime

val now = LocalDateTime.now()
val newsRepository = NewsRepository().apply{
    addNews(
        "Welcome to Ekhagen",
        "In ekhagen you can find........",
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

    fun getNewsById(id:Int) = multipleNews.remove(multipleNews.find {
        it.id == id
    })

    fun deleteNewsById(id:Int) = multipleNews.remove(multipleNews.find {
        println(it.id)
        it.id == id

    })

    fun updateNewsById(id: Int, newTitle: String, newContent:String, newDate:String){
        getNewsById(id)?.run {
            newTitle
        }
    }
}
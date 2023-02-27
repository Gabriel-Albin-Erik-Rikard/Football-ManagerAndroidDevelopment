package com.example.football_manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.time.LocalDateTime


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           AppScreen()
        }
    }
}

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
            //title = newTitle
            //content = newContent
            //date = newDate
        }
    }

}

@Composable
fun AppScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "viewAll"
    ){
        composable("viewAll") {
            ViewAllScreen(navController, newsRepository.getAllNews())
        }
    }
}

@Composable
fun ViewAllScreen(navController: NavHostController, listy: List<News>) {

    LazyColumn(
        modifier = Modifier.padding(24.dp)
    ){
        items(listy) {News ->
            Column(modifier = Modifier.clickable { navController.navigate("viewwOne/${News.id}") }) {
                Text(News.title, style = MaterialTheme.typography.titleLarge)
                println(News.title)
                Divider()
            }
        }
    }

}


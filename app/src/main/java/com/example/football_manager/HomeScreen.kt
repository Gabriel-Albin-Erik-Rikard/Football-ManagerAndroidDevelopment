package com.example.football_manager

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.filament.Box
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.time.LocalDateTime


//FREE TO CHANGE

data class News(
    val id: Int,
    var title: String,
    var content: String,
    var date: String,
    var writer: String
)



val now = LocalDateTime.now()
val newsRepository = NewsRepository().apply {
    addNews(
        "Welcome to Ekhagen",
        "In ekhagen you can find........",
        date = now.toString(),
        "Gabriel Adward"
    )
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

@ExperimentalMaterial3Api
@Composable
fun AppScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "viewAll"
    ) {
        composable("viewAll") {
            ViewAllScreen(navController, newsRepository.getAllNews())
        }
        composable("viewOne/{id}") {
            val id = it.arguments!!.getString("id")!!.toInt()
            ViewOneScreen(id, navController)
        }

        composable("viewOneEditedNews/{id}"){
            val id  = it.arguments!!.getString("id")!!.toInt()
            EditNews(id, navController)

        }
        composable("createNews") {
            createNews(navController = navController)
        }
    }
}


@Composable
fun ViewAllScreen(navController: NavHostController, listy: List<News>) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier.padding(24.dp)
        ) {
            item {
                androidx.compose.material3.Text(
                    text = "Welcome to News Page: ",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
            }

            items(listy) { News ->
                Column(modifier = Modifier.clickable { navController.navigate("ViewOne/${News.id}") }) {
                    androidx.compose.material3.Surface(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(150.dp),
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .width(1100.dp)
                    ) {
                        androidx.compose.material3.Text(
                            text = News.title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 78.dp, vertical = 15.dp)
                        )
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            androidx.compose.material3.FloatingActionButton(
                onClick = {
                    navController.navigate("createNews")
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
            ) {
                androidx.compose.material3.Text(
                    text = "+",
                    fontSize = 35.sp,
                    color = androidx.compose.ui.graphics.Color.White

                )
            }
        }
    }
}












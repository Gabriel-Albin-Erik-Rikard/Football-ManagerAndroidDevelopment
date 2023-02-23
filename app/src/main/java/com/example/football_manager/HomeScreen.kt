package com.example.football_manager

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.filament.Box
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


data class News(
    var title : String,
    var content : String,
)

val newsList = mutableListOf<News>()
/*class HomeScreenViewModel {
    private val newsList = mutableListOf<News>()
    fun addNews(news: News) {
        newsList.add(news)
    }
    fun getNewsList(): List<News> {
        return newsList
    }
    fun getNews(){
        //TODO  hämta från server
        //newsList.add(News("He died", "it was a horrible death"))
    }

}*/



@Composable
fun HomeScreen() {
    val newsList = mutableListOf<News>()
    newsList.add(News("Yikes", "Didnt go well"))
    newsList.add(News("Yikers", "Didnt go well"))
    newsList.add(News("U cant beliewe what happens if u press this button", "Didnt go well"))
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally

    )
            {
            item{
                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.ui.graphics.Color.Green)
                    ) {
                    Text(text = "Create")
                }
            }
                //maybe use text here instead of buttons?
            items(newsList){ News ->
                /*Text(
                    text = News.title,
                    modifier = Modifier
                        .clickable{
                            //TODO, show the news.
                        }
                    )*/
                Button(onClick = { /*TODO take me to the news */ }) {
                    Text(text = News.title)
            }
    }
}


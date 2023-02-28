package com.example.football_manager

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.filament.Box
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


//FREE TO CHANGE

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
    val startDestination : String = "HomeScreenStart"
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable("HomeScreenStart") {
            HomeScreenStart(navController)
        }
        composable("HomeScreenNews") {
            HomeScreenNews(navController)
        }
    }
}

@Composable
fun HomeScreenStart(navController: NavController){
    val newsList = mutableListOf<News>()
    newsList.add(News("Yikes", "Didnt go well"))
    newsList.add(News("Yikers", "Didnt go well"))
    newsList.add(News("Yikes", "Didnt go well"))
    newsList.add(News("Yikers", "Didnt go well"))
    newsList.add(News("U cant beliewe what happens if u press this button in a veryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryvery long way", "Didnt go well"))
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)


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
        //Gillar Card
        items(newsList){ News ->
            Card(
                //shape =
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .height(180.dp)
                    .width(280.dp)
                    //.alignment = Alignment.Center
                    //.background(androidx.compose.ui.graphics.Color.LightGray)
                    .padding(12.dp)
                    .clickable {
                        //ta mig vidare här
                        navController.navigate("HomeScreenNews")
                        println("hejhej")
                    }
            ) {
                Text(
                    text = News.title,
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        //.padding(12.dp)
                        .background(LightGray)
                )
            }
        }
    }
}


@Composable
fun HomeScreenNews(navController: NavController){
    navController.currentDestination
    Box(modifier = Modifier
        .background(Gray),
        contentAlignment = Alignment.Center
    ){
        Text(text = "senjor!")
    }
}


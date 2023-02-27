package com.example.football_manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.time.LocalDateTime
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.runtime.remember



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

    fun getNewsById(id:Int) = multipleNews.find{
        it.id == id
    }

    fun deleteNewsById(id:Int) = multipleNews.remove(multipleNews.find {
        println(it.id)
        it.id == id

    })

    fun updateNewsById(id: Int, newTitle: String, newContent:String){
        getNewsById(id)?.run {
            title = newTitle
            content = newContent
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
        composable("viewOne/{id}") {
            val id = it.arguments!!.getString("id")!!.toInt()
            ViewOneScreen(id, navController)
        }
        composable("createNews"){
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
        ){
            item{
                Text(text = "Welcome to News Page: ",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
            }

            items(listy) {News ->
                Column(modifier = Modifier.clickable { navController.navigate("ViewOne/${News.id}") }) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(150.dp),
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .width(1100.dp)
                    ) {
                        Text(
                            text = News.title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 78.dp, vertical = 15.dp)
                        )
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize() ){
            FloatingActionButton(onClick ={
                navController.navigate("createNews")
            },
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
            ){
                Text(
                    text = "+",
                    fontSize = 35.sp,
                    color = Color.White

                )
            }
        }
    }
}

@Composable
fun ViewOneScreen(id: Int, navController: NavHostController) {
    val singleNews = newsRepository.getNewsById(id)
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = " ${singleNews?.title}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = " ${singleNews?.content}", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(60.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = "Date: ${singleNews?.date?.substring(0, 10)}",
                    style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Writer: ${singleNews?.writer}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(0.5f)
                )
            }
            Spacer(modifier = Modifier.height(200.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { /* Handle first button click */ }) {
                    Text(text = "Edit News")
                }
                Button(onClick = { /* Handle second button click */ }) {
                    Text(text = "Delete News")
                }
            }
        }
    }

@Composable
fun createNews(navController: NavHostController){
    Column(
        modifier = Modifier.padding(80.dp)
    ){
        var newTextTitle by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = newTextTitle, 
            onValueChange = {newTextTitle = it},
            label = { Text(text = "Your Title")},
            placeholder = { Text(text = "Write Your Title")}
        )

        var newTextContent by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = newTextContent,
            onValueChange = {newTextContent = it},
            label = { Text(text = "Your Title")},
            placeholder = { Text(text = "Write Your Title")}
        )
        var newWriter by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = newWriter,
            onValueChange = {newWriter = it},
            label = { Text(text = "Your Title")},
            placeholder = { Text(text = "Write Your Title")}
        )

        var titleText = newTextTitle.text
        var contentText = newTextContent.text
        var writerText = newWriter.text
        Button(onClick = {
            newsRepository.addNews(title = titleText, content = contentText, date = now.toString(), writer = writerText)
            navController.popBackStack()
        }) {
            Text(text = "Create News")
        }
    }
}







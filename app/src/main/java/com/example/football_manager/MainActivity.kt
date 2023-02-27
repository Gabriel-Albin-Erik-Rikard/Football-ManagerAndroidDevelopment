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
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.input.TextFieldValue


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
fun createNews(navController: NavHostController) {
    val errors = remember { mutableStateListOf<String>() }
    val NEWS_TITLE_MIN_LENGTH = 3
    val NEWS_TITLE_MAX_LENGTH = 30
    val NEWS_WRITER_NAME_MIN_LENGTH = 4
    val NEWS_WRITER_NAME_MAX_LENGTH = 10
    val NEWS_CONTENT_MIN_LENGTH = 10
    val NEWS_CONTENT_MAX_LENGTH = 120


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var newTextTitle by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = newTextTitle,
                onValueChange = { newTextTitle = it },
                label = { Text(text = "Your Title") },
                placeholder = { Text(text = "Write Your Title") }
            )

            var newTextContent by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = newTextContent,
                onValueChange = { newTextContent = it },
                label = { Text(text = "Your Content") },
                placeholder = { Text(text = "Write Your Content") }
            )

            var newWriter by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = newWriter,
                onValueChange = { newWriter = it },
                label = { Text(text = "Your Writer") },
                placeholder = { Text(text = "Write Your Writer") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            var titleText = newTextTitle.text
            var contentText = newTextContent.text
            var writerText = newWriter.text
            Button(
                onClick = {
                    errors.clear()
                    if (titleText.isEmpty() or contentText.isEmpty() or writerText.isEmpty()) {
                        errors.add("Check So No Fields Are Empty")
                        println("Hello")
                    } else if ((titleText.length < NEWS_TITLE_MIN_LENGTH || titleText.length > NEWS_TITLE_MAX_LENGTH)) {
                        errors.add("The Title Should Be Between 3-30 Characters")
                    } else if (contentText.length < NEWS_CONTENT_MIN_LENGTH || contentText.length > NEWS_CONTENT_MAX_LENGTH) {
                        errors.add("The Content Should Be Between 10-120 Characters")
                    } else if (writerText.length < NEWS_WRITER_NAME_MIN_LENGTH || writerText.length > NEWS_WRITER_NAME_MAX_LENGTH) {
                        errors.add("The Writers Name Should Be Between 4-10 Characters")
                    } else {
                        newsRepository.addNews(
                            title = titleText,
                            content = contentText,
                            date = now.toString(),
                            writer = writerText
                        )
                        navController.popBackStack()

                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Create News")
            }
            for (error in errors) {
                Text(error)
            }
        }
    }
}







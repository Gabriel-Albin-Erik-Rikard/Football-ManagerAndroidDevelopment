package com.example.football_manager

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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
import java.lang.Math.ceil
import java.time.LocalDateTime
import java.util.Collections.min
import kotlin.math.ceil
import kotlin.math.min


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
fun HomeScreen() {
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
            CreateNews(navController = navController)
        }
    }
}

@Composable
fun ViewAllScreen(navController: NavHostController, listy: List<News>, itemsPerPage: Int = 4) {
    val pageCount = ceil(listy.size.toFloat() / itemsPerPage).toInt()
    var currentPage by remember { mutableStateOf(1) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Button(
                    onClick = {
                        navController.navigate("createNews")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.ui.graphics.Color.Cyan),
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = "Create News",
                        fontSize = 20.sp,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                }
            }

            val startIndex = (currentPage - 1) * itemsPerPage
            val endIndex = min(startIndex + itemsPerPage, listy.size)
            val sublist = listy.subList(startIndex, endIndex)

            items(sublist) { news ->
                Column(modifier = Modifier.clickable { navController.navigate("ViewOne/${news.id}") }) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(150.dp),
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .width(1100.dp)
                    ) {
                        Text(
                            text = news.title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 78.dp, vertical = 15.dp)
                        )
                    }
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { if (currentPage > 1) currentPage-- },
                        enabled = currentPage > 1
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Previous Page"
                        )
                    }

                    Text(
                        text = "Page $currentPage of $pageCount",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    IconButton(
                        onClick = { if (currentPage < pageCount) currentPage++ },
                        enabled = currentPage < pageCount
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next Page"
                        )
                    }
                }
            }
        }
    }
}






@Composable
fun ViewOneScreen(id: Int, navController: NavHostController) {
    val singleNews = newsRepository.getNewsById(id)

    Column(modifier = Modifier.padding(10.dp)) {
        androidx.compose.material3.Text(
            text = " ${singleNews?.title}",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(
            modifier = Modifier
                .height(50.dp)
                .padding(10.dp)
        )
        androidx.compose.material3.Text(
            text = " ${singleNews?.content}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(60.dp))

        Row(modifier = Modifier.fillMaxWidth()) {

            androidx.compose.material3.Text(
                text = "Date: ${singleNews?.date?.substring(0, 10)}",
                style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f)
            )
            androidx.compose.material3.Text(
                text = "Writer: ${singleNews?.writer}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(100.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material3.Button(onClick = { navController.navigate("viewOneEditedNews/${id}") }) {
                androidx.compose.material3.Text(text = "Edit News")
            }
            val openDialog = remember { mutableStateOf(false) }

            androidx.compose.material3.Button(onClick = { openDialog.value = true }) {
                androidx.compose.material3.Text(text = "Delete News")
            }
            if (openDialog.value) {
                androidx.compose.material3.AlertDialog(onDismissRequest = {
                    openDialog.value = false
                },
                    title = {
                        androidx.compose.material3.Text(text = "Are You Sure?")
                    },
                    confirmButton = {
                        androidx.compose.material3.Button(onClick = {
                            openDialog.value = false
                            newsRepository.deleteNewsById(id)
                            navController.popBackStack()
                        }) {
                            androidx.compose.material3.Text(text = "Yes!")

                        }

                    },
                    dismissButton = {
                        androidx.compose.material3.Button(
                            onClick = { openDialog.value = false }) {
                            androidx.compose.material3.Text(text = "No!")

                        }
                    }

                )


            }
        }

    }
}

@ExperimentalMaterial3Api
@Composable
fun CreateNews(navController: NavHostController) {
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
                label = { androidx.compose.material3.Text(text = "Your Title") },
                placeholder = { androidx.compose.material3.Text(text = "Write Your Title") }
            )

            var newTextContent by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = newTextContent,
                onValueChange = { newTextContent = it },
                label = { androidx.compose.material3.Text(text = "Your Content") },
                placeholder = { androidx.compose.material3.Text(text = "Write Your Content") }
            )

            var newWriter by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = newWriter,
                onValueChange = { newWriter = it },
                label = { androidx.compose.material3.Text(text = "Your Writer") },
                placeholder = { androidx.compose.material3.Text(text = "Write Your Writer") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            var titleText = newTextTitle.text
            var contentText = newTextContent.text
            var writerText = newWriter.text
            androidx.compose.material3.Button(
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
                androidx.compose.material3.Text(text = "Create News")
            }
            for (error in errors) {
                androidx.compose.material3.Text(error)
            }
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun EditNews(id: Int, navController: NavHostController){

    val errors = remember { mutableStateListOf<String>() }
    val EDIT_NEWS_TITLE_MIN_LENGTH = 3
    val EDIT_NEWS_TITLE_MAX_LENGTH = 30
    val EDIT_NEWS_CONTENT_MIN_LENGTH = 10
    val EDIT_NEWS_CONTENT_MAX_LENGTH = 120

    val CurrentTaskID = newsRepository.getNewsById(id)
    var CurrentTitle = CurrentTaskID?.title
    var CurrentDescription = CurrentTaskID?.content

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var InputTitleTextOfNews by remember { mutableStateOf(TextFieldValue(CurrentTitle.toString())) }
            OutlinedTextField(
                value = InputTitleTextOfNews,
                onValueChange = {
                    InputTitleTextOfNews = it
                },
                label = { androidx.compose.material3.Text("Your Title") },
                placeholder = { androidx.compose.material3.Text("Write your title") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            var InputContentOfNews by remember { mutableStateOf(TextFieldValue(CurrentDescription.toString())) }
            OutlinedTextField(
                value = InputContentOfNews,
                onValueChange = {
                    InputContentOfNews = it
                },
                label = { androidx.compose.material3.Text("Your Content") },
                placeholder = { androidx.compose.material3.Text("Write Your Content") }
            )


            var updateTitleField = InputTitleTextOfNews.text
            var updateContentField = InputContentOfNews.text
            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material3.Button(
                onClick = {
                    errors.clear()
                    if (updateTitleField.isEmpty() or updateContentField.isEmpty()) {
                        errors.add("Check So No Fields Are Empty")
                        println("Hello")
                    } else if ((updateTitleField.length < EDIT_NEWS_TITLE_MIN_LENGTH || updateTitleField.length > EDIT_NEWS_TITLE_MAX_LENGTH)) {
                        errors.add("The Title Should Be Between 3-30 Characters")
                    } else if (updateContentField.length < EDIT_NEWS_CONTENT_MIN_LENGTH || updateContentField.length > EDIT_NEWS_CONTENT_MAX_LENGTH) {
                        errors.add("The Content Should Be Between 10-120 Characters")
                    } else {

                        newsRepository.updateNewsById(
                            id,
                            newTitle = updateTitleField,
                            newContent = updateContentField
                        )
                        navController.popBackStack()
                    }

                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                androidx.compose.material3.Text("Submit")
            }
            for (error in errors) {
                androidx.compose.material3.Text(error)
            }
        }
    }
}







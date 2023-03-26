package com.example.football_manager.News

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.football_manager.viewmodel.NewsViewModel


@Composable
fun CreateNews(navController: NavHostController) {
    val errors = remember { mutableStateListOf<String>() }
    var newsViewModel = NewsViewModel()
    newsViewModel.addNews(title = "", content = "", date = "", writer = "")
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

            val titleText = newTextTitle.text
            val contentText = newTextContent.text
            val writerText = newWriter.text
            androidx.compose.material3.Button(
                onClick = {
                    errors.clear()
                    if (titleText.isEmpty() or contentText.isEmpty() or writerText.isEmpty()) {
                        errors.add("Check So No Fields Are Empty")
                        println("Hello")
                    } else if ((titleText.length < NEWS_TITLE_MIN_LENGTH || titleText.length > NEWS_TITLE_MAX_LENGTH)) {
                        errors.add("The Title Should Be Between 3-30 Characters")
                    } else if (contentText.length < NEWS_CONTENT_MIN_LENGTH) {
                        errors.add("The Content Should Be At Least 10 Characters")
                    } else if (writerText.length < NEWS_WRITER_NAME_MIN_LENGTH || writerText.length > NEWS_WRITER_NAME_MAX_LENGTH) {
                        errors.add("The Writers Name Should Be Between 4-10 Characters")
                    } else {
                        newsViewModel.addNews(title = titleText, content = contentText, date = "", writer = writerText)
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

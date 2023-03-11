package com.example.football_manager.News

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


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

            val titleText = newTextTitle.text
            val contentText = newTextContent.text
            val writerText = newWriter.text
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

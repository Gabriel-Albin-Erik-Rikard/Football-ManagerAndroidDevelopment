package com.example.football_manager.News

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun EditNews(id: Int, navController: NavHostController){

    val errors = remember { mutableStateListOf<String>() }
    val EDIT_NEWS_TITLE_MIN_LENGTH = 3
    val EDIT_NEWS_TITLE_MAX_LENGTH = 30
    val EDIT_NEWS_CONTENT_MIN_LENGTH = 10
    val EDIT_NEWS_CONTENT_MAX_LENGTH = 120

    val currentTaskID = newsRepository.getNewsById(id)
    val currentTitle = currentTaskID?.title
    val currentDescription = currentTaskID?.content

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var InputTitleTextOfNews by remember { mutableStateOf(TextFieldValue(currentTitle.toString())) }
            OutlinedTextField(
                value = InputTitleTextOfNews,
                onValueChange = {
                    InputTitleTextOfNews = it
                },
                label = { Text("Your Title") },
                placeholder = { Text("Write your title") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            var InputContentOfNews by remember { mutableStateOf(TextFieldValue(currentDescription.toString())) }
            OutlinedTextField(
                value = InputContentOfNews,
                onValueChange = {
                    InputContentOfNews = it
                },
                label = { Text("Your Content") },
                placeholder = { Text("Write Your Content") }
            )


            val updateTitleField = InputTitleTextOfNews.text
            val updateContentField = InputContentOfNews.text
            Spacer(modifier = Modifier.height(16.dp))

            Button(
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
                Text("Submit")
            }
            for (error in errors) {
                Text(error)
            }
        }
    }
}







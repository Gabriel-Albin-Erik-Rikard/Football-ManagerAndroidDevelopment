package com.example.football_manager.News

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.football_manager.model.News
import com.example.football_manager.viewmodel.NewsViewModel





/*
fun EditNews(singleNews: News, navController: NavHostController){

    val errors = remember { mutableStateListOf<String>() }
    var newsViewModel = NewsViewModel()
    newsViewModel.updateNews( singleNews.id, singleNews.title, singleNews.content)

    val currentTitle = singleNews.title
    val currentDescription = singleNews.content

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
                label = { androidx.compose.material3.Text("Your Title") },
                placeholder = { androidx.compose.material3.Text("Write your title") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            var InputContentOfNews by remember { mutableStateOf(TextFieldValue(currentDescription.toString())) }
            OutlinedTextField(
                value = InputContentOfNews,
                onValueChange = {
                    InputContentOfNews = it
                },
                label = { androidx.compose.material3.Text("Your Content") },
                placeholder = { androidx.compose.material3.Text("Write Your Content") }
            )


            val updateTitleField = InputTitleTextOfNews.text
            val updateContentField = InputContentOfNews.text
            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material3.Button(
                onClick = {
                    errors.clear()
                    if (updateTitleField.isEmpty() || updateContentField.isEmpty()) {
                        errors.add("Check So No Fields Are Empty")
                    } else if ((updateTitleField.length < NEWS_TITLE_MIN_LENGTH || updateTitleField.length > NEWS_TITLE_MAX_LENGTH)) {
                        errors.add("The Title Should Be Between 3-30 Characters")
                    } else if (updateContentField.length < NEWS_CONTENT_MIN_LENGTH) {
                        errors.add("The Content Should Have At Least 10 Characters")
                    } else {

                        singleNews.title = updateTitleField
                        singleNews.content = updateContentField
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
*/
package com.example.football_manager.News

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.football_manager.model.News
import java.text.SimpleDateFormat
import java.util.*




@Composable
fun ViewOneNewsScreen(singleNews: News, navController: NavHostController) {



    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxSize()
    ) {
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

            val currentDate = remember { Calendar.getInstance().time }
            val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(currentDate)
            androidx.compose.material3.Text(
                text = "Date:  $formattedDate",
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material3.Button(
                onClick = { navController.navigate("viewOneEditedNews/${singleNews.id}") },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                androidx.compose.material3.Text(text = "Edit News")
            }
            val openDialog = remember { mutableStateOf(false) }

            androidx.compose.material3.Button(
                onClick = { openDialog.value = true },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                androidx.compose.material3.Text(text = "Delete News")
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        androidx.compose.material3.Text(text = "Are You Sure?")
                    },
                    confirmButton = {
                        androidx.compose.material3.Button(onClick = {
                            openDialog.value = false
                            newsRepository.deleteNewsById(singleNews.id)
                            navController.popBackStack()
                        }) {
                            androidx.compose.material3.Text(text = "Yes!")
                        }
                    },
                    dismissButton = {
                        androidx.compose.material3.Button(
                            onClick = { openDialog.value = false }
                        ) {
                            androidx.compose.material3.Text(text = "No!")
                        }
                    }
                )
            }
        }
    }
}

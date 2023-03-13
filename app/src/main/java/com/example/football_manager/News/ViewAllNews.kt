package com.example.football_manager.News

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.football_manager.model.News


@Composable
fun ViewAllScreen(navController: NavHostController, listy: List<News>) {
    val sortedList = listy.sortedBy { it.date }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    navController.navigate("createNews")
                },
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    text = "Create News",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }

        sortedList.forEach { news ->
            Column(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .clickable { navController.navigate("ViewOne/${news.id}") }
            ) {
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
                        modifier = Modifier.padding(horizontal = 25.dp, vertical = 15.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}


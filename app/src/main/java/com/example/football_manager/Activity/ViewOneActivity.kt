package com.example.football_manager.Activity

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.football_manager.model.Activity

@Composable
fun ViewOneScreen(id: Int, navController: NavHostController, activity:Activity) {
    val singleActivity: Activity? = Activity(id = id, type = "Training")

    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxSize()
    ) {
        Text(
            text = " ${singleActivity?.type}",
            style = MaterialTheme.typography.titleLarge
        )
    }
}



package com.example.football_manager.Activity

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.football_manager.MainActivity
import com.example.football_manager.model.Activities
import com.example.football_manager.viewmodel.PersonViewModel

@Composable
fun ViewAllScreen(
    navController: NavHostController,
    activities: List<Activities>,
) {

    // Show all activities

    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Activities",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        activities.forEach { activity ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        navController.navigate("viewOneActivity/${activity.id}")
                    },
                shape = RoundedCornerShape(10.dp),
                color = Color.LightGray
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Title : ${activity.type}",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Time : ${activity.startDate?.format("%02d:%02d")}",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }

}


package com.example.football_manager.Activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.football_manager.model.Activities

@Composable
fun ViewOneScreen(activity: Activities) {
    // Show one activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = "Title : ${activity.type}")
        Text(text = "Start Date : ${activity.startDate?.substring(0, 10)}")
    }

}



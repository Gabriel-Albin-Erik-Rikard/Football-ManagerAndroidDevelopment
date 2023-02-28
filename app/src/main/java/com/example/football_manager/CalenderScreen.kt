package com.example.football_manager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


data class Activities(
    val id: Int,
    var matchType: String,
    var title: String ,
    var description: String ,
    var week: Int,
    var date: String,
    var time: String
)
@Composable
fun CalenderScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green),
    ){
        Text(text = "CALENDER")
    }
}
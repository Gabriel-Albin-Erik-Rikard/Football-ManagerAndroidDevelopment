package com.example.football_manager


import androidx.compose.runtime.*

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.football_manager.Activity.CreateActivity
import com.example.football_manager.Activity.EditActivity
import com.example.football_manager.Activity.ViewAllScreen
import com.example.football_manager.Activity.ViewOneScreen


const val ACTIVITY_TITLE_MIN_LENGTH = 4
const val ACTIVITY_TITLE_MAX_LENGTH = 20
const val ACTIVITY_MATCHTYPE_MIN_LENGTH = 4
const val ACTIVITY_MATCHTYPE_MAX_LENGTH = 15
const val ACTIVITY_DESCRIPTION_MIN_LENGTH = 10

@Composable
fun CalenderScreen(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "viewAll"
    ) {
        composable("viewAll") {
            ViewAllScreen(navController)
        }
        composable("viewOne/{id}") {
            val id = it.arguments!!.getString("id")!!.toInt()
            ViewOneScreen(id, navController)
        }

        composable("viewOneEditedActivity/{id}"){
            val id  = it.arguments!!.getString("id")!!.toInt()
            EditActivity(id, navController)

        }
        composable("createActivity") {
            CreateActivity(navController = navController)
        }
    }
}







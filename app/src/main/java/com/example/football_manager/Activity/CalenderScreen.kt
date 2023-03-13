package com.example.football_manager


import androidx.compose.runtime.*

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.football_manager.Activity.CreateActivity
import com.example.football_manager.Activity.EditActivity
import com.example.football_manager.Activity.ViewAllScreen
import com.example.football_manager.Activity.ViewOneScreen


data class Activities(
    val id: Int,
    var matchType: String,
    var title: String ,
    var description: String ,
    var date: String,
    var startTime: String,
    var finishTime: String

)

val activityRepository = ActivityRepository().apply{
    addActivity(
        matchType = "Training",
        title = "Training",
        description = "A friendly match between Team A and Team B",
        date = "07/02/2023",
        startTime = "14:00",
        finishTime = "17:00"
    )
    addActivity(
        matchType = "Match",
        title = "Råslätt vs Ekhagen",
        description = "A friendly match between Team A and Team B",
        date = "08/03/2023",
        startTime = "14:00",
        finishTime = "17:00"
    )
    addActivity(
        matchType = "Match",
        title = "Råslätt vs Ekhagen",
        description = "A friendly match between Team A and Team B",
        date = "12/03/2023",
        startTime = "14:00",
        finishTime = "17:00"
    )
    addActivity(
        matchType = "Match",
        title = "Råslätt vs Ekhagen",
        description = "A friendly match between Team A and Team B",
        date = "12/03/2023",
        startTime = "14:00",
        finishTime = "17:00"
    )
    addActivity(
        matchType = "Match",
        title = "Råslätt vs Ekhagen",
        description = "A friendly match between Team A and Team B",
        date = "12/03/2023",
        startTime = "14:00",
        finishTime = "17:00"
    )
    addActivity(
        matchType = "Match",
        title = "Råslätt vs Ekhagen",
        description = "A friendly match between Team A and Team B",
        date = "12/03/2023",
        startTime = "14:00",
        finishTime = "17:00"
    )
    addActivity(
        matchType = "Match",
        title = "Råslätt vs Ekhagen",
        description = "A friendly match between Team A and Team B",
        date = "12/03/2023",
        startTime = "14:00",
        finishTime = "17:00"
    )
}

class ActivityRepository {
    private val multipleActivities = mutableListOf<Activities>()

    fun addActivity(
        matchType: String,
        title: String,
        description: String,
        date: String,
        startTime: String,
        finishTime: String
    ): Int {
        val id = when {
            multipleActivities.isEmpty() -> 1
            else -> multipleActivities.last().id + 1
        }
        multipleActivities.add(
            Activities(
                id,
                matchType,
                title,
                description,
                date,
                startTime,
                finishTime
            )
        )
        return id
    }

    fun getAllActivities() = multipleActivities

    fun getActivityById(id: Int) = multipleActivities.find {
        it.id == id
    }

    fun deleteActivityById(id: Int): Boolean {
        val activityToRemove = multipleActivities.find { it.id == id }
        if (activityToRemove != null) {
            return multipleActivities.remove(activityToRemove)
        }
        return false
    }

    fun updateActivityById(
        id: Int,
        newMatchType: String,
        newTitle: String,
        newDescription: String,
        newDate: String,
        newStartTime: String,
        newFinishTime: String

    ) {
        getActivityById(id)?.run {
            matchType = newMatchType
            title = newTitle
            description = newDescription
            date = newDate
            startTime = newStartTime
            finishTime= newFinishTime
        }
    }
}


@Composable
fun CalenderScreen(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "viewAll"
    ) {
        composable("viewAll") {
            ViewAllScreen(navController, activityRepository.getAllActivities())
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







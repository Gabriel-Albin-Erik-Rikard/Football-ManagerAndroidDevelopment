package com.example.football_manager


import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.*

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.football_manager.Activity.CreateActivity
import com.example.football_manager.Activity.EditActivity
import com.example.football_manager.Activity.ViewAllScreen
import com.example.football_manager.Activity.ViewOneScreen
import com.example.football_manager.MainActivity.Companion.context
import com.example.football_manager.viewmodel.PersonViewModel


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
        title = "Mullsjö vs Ekhagen",
        description = "A friendly match between Team A and Team B",
        date = "12/03/2023",
        startTime = "14:00",
        finishTime = "17:00"
    )
    addActivity(
        matchType = "Match",
        title = "Habo vs Ekhagen",
        description = "A friendly match between Team A and Team B",
        date = "12/03/2023",
        startTime = "14:00",
        finishTime = "17:00"
    )
    addActivity(
        matchType = "Match",
        title = "J-södra vs Ekhagen",
        description = "A friendly match between Team A and Team B",
        date = "12/03/2023",
        startTime = "14:00",
        finishTime = "17:00"
    )
    addActivity(
        matchType = "Match",
        title = "Husqvarna vs Ekhagen",
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

const val ACTIVITY_TITLE_MIN_LENGTH = 4
const val ACTIVITY_TITLE_MAX_LENGTH = 20
const val ACTIVITY_MATCHTYPE_MIN_LENGTH = 4
const val ACTIVITY_MATCHTYPE_MAX_LENGTH = 15
const val ACTIVITY_DESCRIPTION_MIN_LENGTH = 10

@Composable
fun CalenderScreen(){
    val navController = rememberNavController()

    val sharedPreferences: SharedPreferences = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)!!
    val id = sharedPreferences.getInt("id", 0)

    val personViewModel = PersonViewModel()

    personViewModel.getPersonActivities(id)

    NavHost(
        navController = navController,
        startDestination = "viewAll"
    ) {
        composable("viewAll") {
            ViewAllScreen(navController, personViewModel.activities)
        }
        composable("viewOne/{id}") {
            val id = it.arguments!!.getString("id")!!.toInt()
            // Get the activity from personViewModel.activities
            val activity = personViewModel.activities.find { it.id == id }!!
            ViewOneScreen(activity)
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







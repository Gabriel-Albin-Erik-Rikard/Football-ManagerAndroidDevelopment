package com.example.football_manager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController


data class Activities(
    val id: Int,
    var matchType: String,
    var title: String ,
    var description: String ,
    var week: Int,
    var date: String,
    var time: String
)

class ActivityRepository {
    private val multipleActivities = mutableListOf<Activities>()

    fun addActivity(
        matchType: String,
        title: String,
        description: String,
        week: Int,
        date: String,
        time: String
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
                week,
                date,
                time
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
        newWeek: Int,
        newDate: String,
        newTime: String
    ) {
        getActivityById(id)?.run {
            matchType = newMatchType
            title = newTitle
            description = newDescription
            week = newWeek
            date = newDate
            time = newTime
        }
    }
}


@Composable
fun CalenderScreen(){

}
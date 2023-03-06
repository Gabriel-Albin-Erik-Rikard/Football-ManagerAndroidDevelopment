package com.example.football_manager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.filament.Material
import kotlin.math.ceil
import kotlin.math.min


data class Activities(
    val id: Int,
    var matchType: String,
    var title: String ,
    var description: String ,
    var week: Int,
    var date: String,
    var time: String
)

val activityDummyData = ActivityRepository().apply{
    addActivity(
        matchType = "Training",
        title = "",
        description = "A friendly match between Team A and Team B",
        week = 1,
        date = "2022-03-07",
        time = "14:00-15.00"
    )
    addActivity(
        matchType = "Match",
        title = "Barcelona vs Real Madrid",
        description = "A league match between Team C and Team D",
        week = 2,
        date = "2022-03-14",
        time = "16:00"
    )
    addActivity(
        matchType = "Match",
        title = "Barcelona vs Real Madrid",
        description = "A league match between Team C and Team D",
        week = 3,
        date = "2022-03-20",
        time = "16:00"
    )
    addActivity(
        matchType = "Match",
        title = "Barcelona vs Real Madrid",
        description = "A league match between Team C and Team D",
        week = 1,
        date = "2022-03-02",
        time = "16:00"
    )
}

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
    val navController  = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "viewAll"
    ){
        composable("viewAll") {
            ViewAllScreen(navController, activityDummyData.getAllActivities())
        }
    }
}

@Composable
fun ViewAllScreen(navController: NavHostController, listy: List<Activities> , itemsPerPage: Int = 2) {
    val sortedList = listy.sortedBy { it.date }
    val pageCount = ceil(sortedList.size.toFloat() / itemsPerPage).toInt()
    var currentPage by remember { mutableStateOf(1) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Button(
                    onClick = {
                        navController.navigate("createActivity")
                    },
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Create Activity",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }

            val startIndex = (currentPage - 1) * itemsPerPage
            val endIndex = min(startIndex + itemsPerPage, listy.size)
            val sublist = sortedList.subList(startIndex, endIndex)

            items(sublist) { activities ->
                Column(
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .clickable { navController.navigate("ViewOne/${activities.id}") }
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(150.dp),
                        modifier = Modifier
                            .padding(horizontal = 40.dp, vertical = 10.dp)
                            .width(600.dp)
                            .height(165.dp)
                    ) { Column() {


                        Column(modifier = Modifier.padding(16.dp)) {
                            if (activities.matchType === "Match") {
                                Text(
                                    text = activities.title,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .padding(start = 55.dp, bottom = 10.dp)
                                        .align(Alignment.CenterHorizontally),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Week " + activities.week.toString(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .padding(start = 45.dp, bottom = 10.dp)
                                        .align(Alignment.CenterHorizontally),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    text = activities.date,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .padding(start = 45.dp, bottom = 10.dp)
                                        .align(Alignment.CenterHorizontally),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = activities.time,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .padding(start = 40.dp, bottom = 10.dp)
                                        .align(Alignment.CenterHorizontally),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            } else if (activities.matchType === "Training") {
                                Text(
                                    text = activities.matchType,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .padding(start = 105.dp, bottom = 10.dp)
                                        .align(Alignment.CenterHorizontally),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Week " + activities.week.toString(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .padding(start = 105.dp, bottom = 15.dp)
                                        .align(Alignment.CenterHorizontally),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    text = activities.date,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .padding(start = 110.dp, bottom = 10.dp)
                                        .align(Alignment.CenterHorizontally),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = activities.time,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .padding(start = 100.dp, bottom = 10.dp)
                                        .align(Alignment.CenterHorizontally),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    }
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { if (currentPage > 1) currentPage-- },
                        enabled = currentPage > 1
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Previous Page"
                        )
                    }
                    Text(
                        text = "Page $currentPage of $pageCount",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    IconButton(
                        onClick = { if (currentPage < pageCount) currentPage++ },
                        enabled = currentPage < pageCount
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next Page"
                        )

                    }
                }
            }
        }
    }
}


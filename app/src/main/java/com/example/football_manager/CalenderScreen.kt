package com.example.football_manager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
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
import kotlin.math.ceil
import kotlin.math.min
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue


data class Activities(
    val id: Int,
    var matchType: String,
    var title: String ,
    var description: String ,
    var week: Int,
    var date: String,
    var time: String
)

val activityRepository = ActivityRepository().apply{
    addActivity(
        matchType = "Training",
        title = "Training",
        description = "A friendly match between Team A and Team B",
        week = 1,
        date = "2022-03-07",
        time = "14:00-15.00"
    )
    addActivity(
        matchType = "Match",
        title = "Råslätt vs Ekhagen",
        description = "A friendly match between Team A and Team B",
        week = 2,
        date = "2022-04-07",
        time = "14:00-15.00"
    )
    addActivity(
        matchType = "Match",
        title = "Råslätt vs Ekhagen",
        description = "A friendly match between Team A and Team B",
        week = 2,
        date = "2022-04-07",
        time = "14:00-15.00"
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

@Composable
fun CreateActivity(navController: NavHostController) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var selectedDateText by remember { mutableStateOf("") }
    var selectedTimeText by remember { mutableStateOf("") }

    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]


    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMonth
    )

    val timePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedTimeText = "$selectedHour:$selectedMinute"
        }, hour, minute, false
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 115.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            var newTextType by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = newTextType,
                onValueChange = { newTextType = it },
                label = { Text(text = "Your MatchType") },
                placeholder = { Text(text = "Write Your MatchType") }
            )

            Spacer(modifier = Modifier.padding(top = 15.dp))

            var newTextTitle by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = newTextTitle,
                onValueChange = { newTextTitle = it },
                label = { Text(text = "Your Title") },
                placeholder = { Text(text = "Write Your Title") }
            )
            Spacer(modifier = Modifier.padding(top = 15.dp))


            var newTextDesc by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = newTextDesc,
                onValueChange = { newTextDesc = it },
                label = { Text(text = "Your Description") },
                placeholder = { Text(text = "Write Your Description") }
            )

            Spacer(modifier = Modifier.padding(top = 15.dp))

            var newTextWeek by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = newTextWeek,
                onValueChange = { newTextWeek = it },
                label = { Text(text = "Chose Week") },
                placeholder = { Text(text = "Write Week ") }
            )

            Spacer(modifier = Modifier.padding(top = 15.dp))



            Text(
                text = if (selectedDateText.isNotEmpty()) {
                    "Selected date is $selectedDateText"
                } else {
                    "Please pick a date"
                }
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Button(
                onClick = {
                    datePicker.show()
                }
            ) {
                Text(text = "Select a date")
            }
            Spacer(modifier = Modifier.padding(top = 15.dp))


            Text(
                text = if (selectedTimeText.isNotEmpty()) {
                    "Selected time is $selectedTimeText"
                } else {
                    "Please select the time"
                }
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Button(
                onClick = {
                    timePicker.show()
                }
            ) {
                Text(text = "Select time")
            }




            Spacer(modifier = Modifier.height(16.dp))

            var titleText = newTextTitle.text
            var typeText = newTextType.text
            var descText = newTextDesc.text
            var weekText = newTextWeek.text
            var chosenDate = selectedDateText
            var chosenTime = selectedTimeText

           Button(
                onClick = {

                        activityRepository.addActivity(
                            title = titleText,
                            matchType = typeText,
                            description = descText,
                            week = weekText.toInt(),
                            date = chosenDate,
                            time = chosenTime
                        )
                        navController.popBackStack()
                    },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Create Activity")
            }

        }
    }
}

@Composable
fun EditActivity(id: Int, navController: NavHostController) {

}

@Composable
fun ViewOneScreen(id: Int, navController: NavHostController) {
    val singleActivity = activityRepository.getActivityById(id)

    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxSize()
    ) {
        Text(
            text = " ${singleActivity?.title}",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(
            modifier = Modifier
                .height(50.dp)
                .padding(10.dp)
        )
        Text(
            text = "Type : ${singleActivity?.matchType}",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = " Week : ${singleActivity?.week}",
            style = MaterialTheme.typography.titleMedium
        )


        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Date : ${singleActivity?.date}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = " Time : ${singleActivity?.time}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(60.dp))


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate("viewOneEditedActivity/${id}") },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Edit Activity")
            }
            val openDialog = remember { mutableStateOf(false) }

            Button(
                onClick = { openDialog.value = true },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Delete Activity")
            }
            if (openDialog.value) {
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Are You Sure?")
                    },
                    confirmButton = {
                        Button(onClick = {
                            openDialog.value = false
                            activityRepository.deleteActivityById(id)
                            navController.popBackStack()
                        }) {
                            Text(text = "Yes!")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { openDialog.value = false }
                        ) {
                            Text(text = "No!")
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun ViewAllScreen(navController: NavHostController, listy: List<Activities> , itemsPerPage: Int = 3) {
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
                Surface(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(150.dp),
                    modifier = Modifier
                        .padding(horizontal = 100.dp, vertical = 15.dp)
                        .width(1100.dp)
                        .height(150.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { navController.navigate("viewOne/${activities.id}") },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally // Changed from Alignment.Start
                    ) {
                        Text(
                            text = activities.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Week " + activities.week.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = activities.date,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = activities.time,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
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




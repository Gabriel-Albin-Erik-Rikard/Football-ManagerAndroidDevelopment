package com.example.football_manager.Activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.football_manager.activityRepository

@Composable
fun EditActivity(id: Int, navController: NavHostController) {
    val errors = remember { mutableStateListOf<String>() }

    val EDIT_ACTIVITY_TITLE_MIN_LENGTH = 4
    val EDIT_ACTIVITY_TITLE_MAX_LENGTH = 20
    val EDIT_ACTIVITY_MATCHTYPE_MIN_LENGTH = 4
    val EDIT_ACTIVITY_MATCHTYPE_MAX_LENGTH = 15
    val EDIT_ACTIVITY_DESCRIPTION_MIN_LENGTH = 10
    val EDIT_ACTIVITY_DESCRIPTION_MAX_LENGTH = 120

    val currentActivityId = activityRepository.getActivityById(id)
    var currentTitle = currentActivityId?.title
    var currentMatchType = currentActivityId?.matchType
    var currentDescription = currentActivityId?.description
    var selectedDate by remember { mutableStateOf(currentActivityId?.date) }
    var selectedStartTime by remember { mutableStateOf(currentActivityId?.startTime) }
    var selectedFinishTime by remember { mutableStateOf(currentActivityId?.finishTime) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var currentSelectedYear = calendar[Calendar.YEAR]
    var currentSelectedMonth = calendar[Calendar.MONTH]
    var currentSelectedDayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    if (currentActivityId?.date != null) {
        val currentDate = currentActivityId.date.split("/")
        currentSelectedYear = currentDate[2].toInt()
        currentSelectedMonth = currentDate[1].toInt() - 1
        currentSelectedDayOfMonth = currentDate[0].toInt()
    }

    val newDatePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDate = "%02d/%02d/%d".format(selectedDayOfMonth, selectedMonth + 1, selectedYear)
        },
        currentSelectedYear, currentSelectedMonth, currentSelectedDayOfMonth
    )

    newDatePicker.datePicker.minDate = calendar.timeInMillis

    val startCalendar = Calendar.getInstance()
    if (currentActivityId?.startTime != null) {
        val startTime = currentActivityId.startTime.split(":")
        startCalendar.set(Calendar.HOUR_OF_DAY, startTime[0].toInt())
        startCalendar.set(Calendar.MINUTE, startTime[1].toInt())
    }
    val currentSelectedHour = startCalendar.get(Calendar.HOUR_OF_DAY)
    val currentSelectedMinute = startCalendar.get(Calendar.MINUTE)

    val newStartTimePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedStartTime = "$selectedHour:${if (selectedMinute < 10) "0$selectedMinute" else selectedMinute}"
        }, currentSelectedHour, currentSelectedMinute, false
    )

    val finishCalendar = Calendar.getInstance()
    if (currentActivityId?.finishTime != null) {
        val finishTime = currentActivityId.finishTime.split(":")
        finishCalendar.set(Calendar.HOUR_OF_DAY, finishTime[0].toInt())
        finishCalendar.set(Calendar.MINUTE, finishTime[1].toInt())
    }
    val currentSelectedHourForFinish = finishCalendar.get(Calendar.HOUR_OF_DAY)
    val currentSelectedMinuteForFinish = finishCalendar.get(Calendar.MINUTE)

    val newEndTimePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedFinishTime = "$selectedHour:${if (selectedMinute < 10) "0$selectedMinute" else selectedMinute}"
        }, currentSelectedHourForFinish, currentSelectedMinuteForFinish, false
    )



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var editedTextTitle by remember { mutableStateOf(TextFieldValue(currentTitle ?: "")) }
            OutlinedTextField(
                value = editedTextTitle,
                onValueChange = { editedTextTitle = it },
                label = { Text(text = "Your Title") },
                placeholder = { Text(text = "Write Your Title") }
            )

            Spacer(modifier = Modifier.padding(top = 15.dp))

            var editedTextMatchType by remember {
                mutableStateOf(
                    TextFieldValue(
                        currentMatchType ?: ""
                    )
                )
            }
            OutlinedTextField(
                value = editedTextMatchType,
                onValueChange = { editedTextMatchType = it },
                label = { Text(text = "Your MatchType") },
                placeholder = { Text(text = "Write Your MatchType") }
            )

            Spacer(modifier = Modifier.padding(top = 15.dp))

            var editedTextDescription by remember {
                mutableStateOf(
                    TextFieldValue(
                        currentDescription ?: ""
                    )
                )
            }
            OutlinedTextField(
                value = editedTextDescription,
                onValueChange = { editedTextDescription = it },
                label = { Text(text = "Your MatchType") },
                placeholder = { Text(text = "Write Your MatchType") }
            )

            Spacer(modifier = Modifier.padding(top = 15.dp))

            Text(
                text = if (selectedDate!!.isNotEmpty()) {
                    "Selected date is $selectedDate"
                } else {
                    "Please pick a date"
                }
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Button(
                onClick = {
                    newDatePicker.show()
                }
            ) {
                Text(text = "Select a date")
            }
            Spacer(modifier = Modifier.padding(top = 15.dp))


            Text(
                text = if (selectedStartTime!!.isNotEmpty()) {
                    "Selected Start-Time is $selectedStartTime"
                } else {
                    "Please select the time"
                }
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Button(
                onClick = {
                    newStartTimePicker.show()
                }
            ) {
                Text(text = "Select Start-Time")
            }

            Text(
                text = if (selectedFinishTime!!.isNotEmpty()) {
                    "Selected Finish-Time is $selectedFinishTime"
                } else {
                    "Please select the time"
                }
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Button(
                onClick = {
                    newEndTimePicker.show()
                }
            ) {
                Text(text = "Select End-Time")
            }
        }
    }
}







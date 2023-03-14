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
import com.example.football_manager.*

@Composable
fun CreateActivity(navController: NavHostController) {

    val errors = remember { mutableStateListOf<String>() }


    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var selectedDateText by remember { mutableStateOf("") }
    var selectedStartTimeText by remember { mutableStateOf("") }
    var selectedEndTimeText by remember { mutableStateOf("") }

    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]


    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "%02d/%02d/%d".format(selectedDayOfMonth, selectedMonth + 1, selectedYear)
        }, year, month, dayOfMonth,
    )

    datePicker.datePicker.minDate = calendar.timeInMillis

    val startTimePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedStartTimeText = "$selectedHour:${if (selectedMinute < 10) "0$selectedMinute" else selectedMinute}"
        }, hour, minute, false
    )


    val endTimePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedEndTimeText = "$selectedHour:${if (selectedMinute < 10) "0$selectedMinute" else selectedMinute}"
        }, hour, minute, false
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 55.dp),
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
                Text(text = "Select a date",
                )
            }
            Spacer(modifier = Modifier.padding(top = 15.dp))


            Text(
                text = if (selectedStartTimeText.isNotEmpty()) {
                    "Selected time is $selectedStartTimeText"
                } else {
                    "Please select the time"
                }
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Button(
                onClick = {
                    startTimePicker.show()
                }
            ) {
                Text(text = "Select Start-Time")
            }

            Spacer(modifier = Modifier.padding(top = 20.dp))

            Text(
                text = if (selectedEndTimeText.isNotEmpty()) {
                    "Selected time is $selectedEndTimeText"
                } else {
                    "Please select the time"
                }
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Button(
                onClick = {
                    endTimePicker.show()
                }
            ) {
                Text(text = "Select End-Time")
            }




            Spacer(modifier = Modifier.height(26.dp))

            val titleText = newTextTitle.text
            val typeText = newTextType.text
            val descText = newTextDesc.text
            val chosenDate = selectedDateText

            Button(
                onClick = {
                    errors.clear()
                    if (titleText.isEmpty() || typeText.isEmpty() || descText.isEmpty()  ){
                        errors.add("Please fill in all fields.")
                    } else if ((titleText.length < ACTIVITY_TITLE_MIN_LENGTH || titleText.length > ACTIVITY_TITLE_MAX_LENGTH)) {
                        errors.add("The Title Should Be Between 4-20 Characters")
                    } else if (typeText.length < ACTIVITY_MATCHTYPE_MIN_LENGTH || typeText.length > ACTIVITY_MATCHTYPE_MAX_LENGTH) {
                        errors.add("The MatchType Should Be Between 4-15 Characters")
                    } else if (descText.length < ACTIVITY_DESCRIPTION_MIN_LENGTH ) {
                        errors.add("The Description Should At Least 10 Characters")
                    } else if (chosenDate.isEmpty()) {
                        errors.add("Please Fill A Date")
                    } else if (selectedStartTimeText.isEmpty() || selectedEndTimeText.isEmpty()) {
                        errors.add("Please Fill Start And Finish Time")
                    } else {
                        activityRepository.addActivity(
                            title = titleText,
                            matchType = typeText,
                            description = descText,
                            date = chosenDate,
                            startTime = "$selectedStartTimeText ",
                            finishTime = "$selectedEndTimeText"
                        )
                        navController.popBackStack()
                    }


                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(240.dp)
                    .padding(top = 55.dp)

            ) {

                Text(text = "Create Activity")
            }
            for (error in errors) {
                Text(error)
            }

        }
    }
}

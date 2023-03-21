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
import com.example.football_manager.model.Activity

@Composable
fun EditActivity(id: Int, navController: NavHostController) {
    val errors = remember { mutableStateListOf<String>() }

    val currentActivityId = Activity(
        id = id,
        startDate = "startTime",
        stopDate = "finishTime",
        type = "matchType",
    )
    var selectedStartTime by remember { mutableStateOf(currentActivityId?.startDate) }
    var selectedFinishTime by remember { mutableStateOf(currentActivityId?.stopDate) }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var editedTextTitle by remember {
                val currentTitle = null
                mutableStateOf(TextFieldValue(currentTitle ?: ""))
            }
            OutlinedTextField(
                value = editedTextTitle,
                onValueChange = { editedTextTitle = it },
                label = { Text(text = "Your Title") },
                placeholder = { Text(text = "Write Your Title") }
            )

            Spacer(modifier = Modifier.padding(top = 15.dp))

            var editedTextMatchType by remember {
                val currentMatchType = ""
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
                val currentDescription = null
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

            val selectedDate = ""
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
                    // TODO
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
                    // TODO
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
                    // TODO
                }
            ) {
                Text(text = "Select End-Time")
            }

            val updateTitleField = editedTextTitle.text
            val updateMatchTypeField = editedTextMatchType.text
            val updateDescriptionField = editedTextDescription.text
            val updateDate = selectedDate.toString()
            val updateStartTime = selectedStartTime.toString()
            val updateFinishTime = selectedFinishTime.toString()


            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    errors.clear()
                    if (updateTitleField.isEmpty() || updateMatchTypeField.isEmpty() || updateDescriptionField.isEmpty()  ){
                        errors.add("Please fill in all fields.")
                    } else if ((updateTitleField.length < ACTIVITY_TITLE_MIN_LENGTH || updateTitleField.length > ACTIVITY_TITLE_MAX_LENGTH)) {
                        errors.add("The Title Should Be Between 4-20 Characters")
                    } else if (updateMatchTypeField.length < ACTIVITY_MATCHTYPE_MIN_LENGTH || updateMatchTypeField.length > ACTIVITY_MATCHTYPE_MAX_LENGTH) {
                        errors.add("The MatchType Should Be Between 4-15 Characters")
                    } else if (updateDescriptionField.length < ACTIVITY_DESCRIPTION_MIN_LENGTH) {
                        errors.add("The Description Name Should Be Between 10-120 Characters")
                    } else if (updateDate.isEmpty()) {
                        errors.add("Please Fill A Date")
                    } else if (updateStartTime!!.isEmpty() || updateFinishTime!!.isEmpty()) {
                        errors.add("Please Fill Start And Finish Time")
                    } else {
                        // TODO Sent to the backend
                        navController.popBackStack()
                    }

                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Submit")
            }
            for (error in errors) {
                Text(error)
            }
        }
    }
}







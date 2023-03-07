package com.example.football_manager.Activity

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.football_manager.activityRepository

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
            text = "Description : ${singleActivity?.description}",
            style = MaterialTheme.typography.titleMedium
        )



        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Date : ${singleActivity?.date}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = " Time : ${singleActivity?.startTime?.format("%02d:%02d")} - ${singleActivity?.finishTime?.format("%02d:%02d")}",
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
                AlertDialog(
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



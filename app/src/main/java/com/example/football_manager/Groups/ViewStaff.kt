package com.example.football_manager.Groups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.football_manager.groupRepository

@Composable
fun StaffPage(navController: NavController, groupID: Int, staffID: Int){
    println(groupID)

    val staff = groupRepository.getGroupById(groupID).getStaffById(staffID)
    showProfile(human = staff)
/*
    Box( modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 50.dp)
        //.background(Color.Blue)
    ) {
        // TODO import an Image here
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Favorite",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopCenter),
            tint = Color.Red
        )
        Card(
            //shape =
            //shape = RoundedCornerShape(12.dp),

            modifier = Modifier
                .height(300.dp)
                .align(Alignment.BottomCenter)
                .width(480.dp)
                //.padding(12.dp)
                .background(Color.LightGray)
        ) {
            Text(
                //text = "testing"
                text = "Name: ${staff.name} \n" +
                        "Role:  ${staff.role}",


                style = MaterialTheme.typography.h4,
                //textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(6.dp)

            )
        }
    }*/
}

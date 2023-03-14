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
fun PlayerPage(navController: NavController, groupID: Int, playerID: Int){
    //println(name)
    val player = groupRepository.getGroupById(groupID).getPlayerById(playerID)
    println("kanske hit 4")
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
            modifier = Modifier
                .height(300.dp)
                .align(Alignment.BottomCenter)
                .width(480.dp)
                //.padding(12.dp)
                .background(Color.LightGray)
        ) {
            Text(
                //text = "testing"
                text = "Name: ${player.name} \n" +
                        "Games Played: ${player.gamesPlayed} \n"+
                        "Goals: ${player.goals} \n"+
                        "Assists: ${player.assists} \n"+
                        "Yellow Cards: ${player.yellowCards} \n"+
                        "Red Cards: ${player.redCards}",
                style = MaterialTheme.typography.h4,
                //textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(6.dp)

            )
        }
    }
}

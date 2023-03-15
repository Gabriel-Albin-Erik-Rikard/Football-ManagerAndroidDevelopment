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
    val player = groupRepository.getGroupById(groupID).getPlayerById(playerID)
    showProfile(human = player)
}

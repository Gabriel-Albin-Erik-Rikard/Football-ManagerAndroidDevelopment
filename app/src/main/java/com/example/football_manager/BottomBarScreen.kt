package com.example.football_manager

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Calender : BottomBarScreen(
        route = "calender",
        title = "Calender",
        icon = Icons.Default.CalendarMonth
    )
    object Teams : BottomBarScreen(
        route = "teams",
        title = "Teams",
        icon = Icons.Default.Groups
    )
    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.AccountCircle
    )
}

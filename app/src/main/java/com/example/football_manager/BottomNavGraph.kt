package com.example.football_manager

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route){
            HomeScreen()
        }
        composable(route = BottomBarScreen.Calender.route){
            CalenderScreen()
        }
        composable(route = BottomBarScreen.Teams.route){
            TeamsScreen()
        }
        composable(route = BottomBarScreen.Profile.route){
            ProfileScreen()
        }
    }
}
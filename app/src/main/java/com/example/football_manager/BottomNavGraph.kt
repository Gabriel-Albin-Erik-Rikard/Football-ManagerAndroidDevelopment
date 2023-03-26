package com.example.football_manager

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.football_manager.Groups.showProfile
import com.example.football_manager.News.HomeScreen
import com.example.football_manager.viewmodel.PersonViewModel

@ExperimentalMaterial3Api

@Composable
fun BottomNavGraph(navController: NavHostController) {
    var personViewModel = PersonViewModel()
    
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
            personViewModel.getPerson(1)
            showProfile(human = personViewModel.person)
            //ProfileScreen()
        }
        composable(route = BottomBarScreen.QR.route){
            QRScreen()
        }
    }
}




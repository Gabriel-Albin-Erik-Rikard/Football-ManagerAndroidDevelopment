package com.example.football_manager

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.selects.select


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController)}
    ) {
        BottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Calender,
        BottomBarScreen.Teams,
        BottomBarScreen.Profile,
        BottomBarScreen.QR,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val  currentDestination = navBackStackEntry?.destination

    BottomNavigation{
        screens.forEach{screen ->
            Additem(screen = screen,
                currentDestination = currentDestination,
                navController =navController
            )
        }
    }
}


@Composable
fun RowScope.Additem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    BottomNavigationItem(
        label = {
            Text(text= screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription =  "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        }
    )
}


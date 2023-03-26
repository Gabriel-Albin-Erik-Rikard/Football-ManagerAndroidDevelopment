package com.example.football_manager


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.football_manager.Groups.*
import com.example.football_manager.model.PersonTeams
import com.example.football_manager.viewmodel.TeamViewModel



open class Human(open val name : String)
data class Staff(
    val id: Int,
    override val name: String,
    var role: String,
): Human(name)


data class Player(
    val id: Int,
    override val name: String,
    val assists: Int,
    var yellowCards : Int,
    var gamesPlayed : Int,
    var goals : Int,
    var redCards : Int,
):Human(name)

@Composable
fun TeamsScreen(teams: PersonTeams){
    val navController = rememberNavController()
    val teamViewModel = TeamViewModel()
    NavHost(
        navController = navController,
        startDestination = "GroupScreenStart"
    ){
        composable("GroupScreenStart") {
            GroupScreenStart(navController,  teams)
        }
        composable("GroupPage/{id}") {
            val id = it.arguments!!.getString("id")!!.toInt()
            teamViewModel.getMembers(id)
            GroupPage(navController, teamViewModel.members)
        }
        composable("PlayerPage/{groupID}/{playerID}") {
            val playerID = it.arguments!!.getString("playerID")!!.toInt()
            val singlePlayer = teamViewModel.members.players!!.find{it.id == id}
            showProfile(human = singlePlayer!!)
        }
        composable("StaffPage/{groupID}/{staffID}") {
            val singelStaff = teamViewModel.members.staff!!.find{it.id == id}
            showProfile(human = singelStaff!!)
        }
    }
}





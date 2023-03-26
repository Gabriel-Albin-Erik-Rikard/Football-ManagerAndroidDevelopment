package com.example.football_manager.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.football_manager.Group
import com.example.football_manager.Player
import com.example.football_manager.Staff
import com.example.football_manager.model.News
import com.example.football_manager.model.Person
import com.example.football_manager.model.PersonTeams
import com.example.football_manager.model.Team
import com.example.football_manager.network.FootballManagerAPIService
import kotlinx.coroutines.launch

data class Members (
    var players: List<Player>? = null,
    var staff: List<Staff>? = null
)
class TeamViewModel : ViewModel() {
    var members: Members by mutableStateOf(Members(listOf(), listOf()))

    //var staff : List<Staff>? by mutableStateOf(listOf())
    //var players:  List<Player>? by mutableStateOf(listOf())
    var errorCode: String by mutableStateOf("")
    //var singlePlayer: Player? by mutableStateOf(Player(0,""))
    //var singleStaff : Staff by mutableStateOf(Staff(0,""))

    fun getMembers (id: Int) {
        viewModelScope.launch {
            val apiService = FootballManagerAPIService.getInstance()
            try {
                var response = apiService.getTeamStaff(id)
                members.staff = response
                var response2 = apiService.getTeamPlayer(id)
                members.players = response2
            } catch (e: Exception) {
                errorCode = e.message.toString()
            }
        }
    }



    /*fun getStaff (id: Int){
        viewModelScope.launch {
            val apiService = FootballManagerAPIService.getInstance()
            try{
                val response = apiService.getTeamStaff()
            }
        }
    }*/

}
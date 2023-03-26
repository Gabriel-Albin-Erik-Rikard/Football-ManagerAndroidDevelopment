package com.example.football_manager.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football_manager.Group
import com.example.football_manager.Player
import com.example.football_manager.Staff
import com.example.football_manager.model.News
import com.example.football_manager.model.Person
import com.example.football_manager.network.FootballManagerAPIService
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {
    var staff : List<Staff>? by mutableStateOf(listOf())
    var players:  List<Player>? by mutableStateOf(listOf())
    var errorCode: String by mutableStateOf("")
    var singlePlayer: Player? by mutableStateOf(Player(0,""))
    var singleStaff : Staff by mutableStateOf(Staff(0,""))

    fun getMembers (id: Int) {
        viewModelScope.launch {
            val apiService = FootballManagerAPIService.getInstance()
            try {
                var response = apiService.getTeamStaff(id)
                staff = response
                var response2 = apiService.getTeamPlayer(id)
                players = response2
                println(response)
            } catch (e: Exception) {
                errorCode = e.message.toString()
            }
        }
    }

    fun getPLayer (id: Int) {
        viewModelScope.launch {
            val apiService = FootballManagerAPIService.getInstance()
            try {
                val response = apiService.getPlayer(id)
                singlePlayer = response
                println(response)
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
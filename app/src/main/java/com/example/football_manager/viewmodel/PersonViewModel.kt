package com.example.football_manager.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football_manager.model.Person
import com.example.football_manager.model.PersonTeams
import com.example.football_manager.model.Team
import com.example.football_manager.network.FootballManagerAPIService
import kotlinx.coroutines.launch

class PersonViewModel: ViewModel() {
    var person: Person by mutableStateOf(Person(0, "", "", "", "", ""))
    var teams: PersonTeams by mutableStateOf(PersonTeams(listOf(), listOf()))
    var errorCode: String by mutableStateOf("")

    // Param: id of the person
    fun getPerson(id: Int) {
        viewModelScope.launch {
            val apiService = FootballManagerAPIService.getInstance()
            try {
                val response = apiService.getPerson(id)
                person = response
                println(response)
            } catch (e: Exception) {
                errorCode = e.message.toString()
            }
        }
    }
    fun getPersonTeams(id: Int) {
        viewModelScope.launch {
            val apiService = FootballManagerAPIService.getInstance()
            try {
                val response = apiService.getPersonTeams(id)
                teams = response
            } catch (e: Exception) {
                errorCode = e.message.toString()
            }
        }
    }
}
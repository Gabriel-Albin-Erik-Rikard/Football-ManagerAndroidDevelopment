package com.example.football_manager.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.football_manager.model.News
import com.example.football_manager.network.FootballManagerAPIService
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    var newsList: List<News> by mutableStateOf(listOf())
    var errorCode: String by mutableStateOf("")

    // Param: id of the person
    fun getPersonNews(id: Int){
        viewModelScope.launch {
            val apiService = FootballManagerAPIService.getInstance()
            try {
                val response = apiService.getPersonNews(id)
                newsList = response
            } catch (e: Exception) {
                errorCode = e.message.toString()
            }
        }
    }

}
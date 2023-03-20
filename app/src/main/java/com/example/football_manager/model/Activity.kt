package com.example.football_manager.model

data class Activity(
    val id: Int? = null,
    var matchType: String? = null,
    var title: String? = null ,
    var description: String? = null ,
    var date: String? = null,
    var startTime: String? = null,
    var finishTime: String? = null,
)

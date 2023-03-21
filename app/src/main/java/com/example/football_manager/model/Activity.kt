package com.example.football_manager.model

data class Activity(
    val id: Int? = null,
    val title: String? = null,
    val type: String,
    var startDate: String? = null,
    var stopDate: String? = null,
) {
}

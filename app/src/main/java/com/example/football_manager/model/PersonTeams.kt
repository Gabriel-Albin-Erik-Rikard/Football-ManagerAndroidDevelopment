package com.example.football_manager.model


data class Team (
    var id: Int? = null,
    var teamName: String? = null,
    var clubId : Int? = null,
    var clubName: String? = null,
    var role : String? = null
)

data class PersonTeams (
    var playerTeams: List<Team>? = null,
    var staffTeams: List<Team>? = null
)

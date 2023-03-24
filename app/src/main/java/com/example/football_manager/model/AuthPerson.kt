package com.example.football_manager.model

data class AuthPerson(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val firebaseId: String? = null,
    val isStaff: Boolean? = false,
    val JWT: String? = null,
    val loggedIn: Boolean? = false
)

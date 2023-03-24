package com.example.football_manager.model

import com.example.football_manager.Human

data class Person(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val gender: String ?= null,
    val phoneNumber: String ?= null,
):Human(firstName)

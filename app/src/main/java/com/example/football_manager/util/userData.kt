package com.example.football_manager.util

class userData {
    private var userId: Int = 0
    private var isCoach: Boolean = false

    fun setUserId(id: Int) {
        this.userId = id
    }

    fun getUserId(): Int {
        return this.userId
    }

    fun setIsCoach(coach: Boolean) {
        this.isCoach = coach
    }

    fun getIsCoach(): Boolean {
        return this.isCoach
    }
}




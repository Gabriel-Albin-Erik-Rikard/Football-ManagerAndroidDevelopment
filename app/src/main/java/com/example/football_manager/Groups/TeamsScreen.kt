package com.example.football_manager

import com.example.football_manager.Groups.GroupScreenStart
import com.example.football_manager.Groups.GroupPage
import com.example.football_manager.Groups.PlayerPage
import com.example.football_manager.Groups.StaffPage


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class Group(var id : Int, var name : String ){
    val players = mutableListOf<Player>()
    val listOfStaff = mutableListOf<Staff>()
    fun addPlayer(name : String){
        val id = when{
            players.isEmpty() -> 0
            else -> players.last().id + 1
        }
        players.add(Player(id, name))
    }
    fun addStaff(name: String){
        val id = when{
            listOfStaff.isEmpty() -> 0
            else -> listOfStaff.last().id + 1
        }
        listOfStaff.add(Staff(id, name))
    }
    fun getPlayerById(id: Int): Player {
        return players[id]
    }

    fun getStaffById(id: Int): Staff {
        return listOfStaff[id]
    }
}


open class Human(open val name : String)
data class Staff(val id : Int, override val name: String): Human(name) {
    var role: String = ""
}
data class Player(val id : Int, override val name : String): Human(name){
    var assists : Int = 0
    var yellowCards : Int = 0
    var gamesPlayed : Int = 0
    var goals : Int = 0
    var redCards : Int = 0
}

//test class, använder ej nu
class PlayerGroupConnector {
    private val playerToGroups = mutableMapOf<Int, MutableList<Int>>()

    fun addPlayerToGroup(playerId: Int, groupId: Int) {
        if (!playerToGroups.containsKey(playerId)) {
            playerToGroups[playerId] = mutableListOf()
        }
        playerToGroups[playerId]?.add(groupId)
    }

    fun getGroupsForPlayer(playerId: Int): MutableList<com.example.football_manager.Group> {
        val groupIds = playerToGroups[playerId]
        val groups = mutableListOf<com.example.football_manager.Group>()
        groupIds?.forEach {
            groups.add(Group(it, "Group $it")) // You can replace this with your own logic for fetching the group data
        }
        return groups
    }
}

val groupRepository = GroupRepository().apply {
    addGroup("SexySwingers")
    addGroup("FinaFynd")
    addGroup("SexySwingers")
    addGroup("FinaFynd")
    addGroup("SexySwingers")
    addGroup("FinaFynd")
    addGroup("SexySwingers")
    addGroup("FinaFynd")

    getGroupById(0).addStaff("Herman")
    getGroupById(0).addStaff("Börje")
    getGroupById(0).addPlayer("Dala")
    getGroupById(0).addPlayer("Jöken")
    getGroupById(0).addPlayer("Kroken")

    getGroupById(0).players[0].goals = 2
}


class GroupRepository(){
    private val listOfGroups = mutableListOf<com.example.football_manager.Group>()

    fun addGroup(name: String)  {
        val id = when {
            listOfGroups.isEmpty() -> 0
            else -> listOfGroups.last().id +1
        }
        listOfGroups.add(
            com.example.football_manager.Group(
                id,
                name
            )
        )
    }

    fun getAllGroups() = listOfGroups
    fun getGroupById(id: Int): com.example.football_manager.Group  {
        return listOfGroups[id]
    }
}

@Composable
fun TeamsScreen(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "GroupScreenStart"
    ){
        composable("GroupScreenStart") {
            GroupScreenStart(navController,  groupRepository.getAllGroups())
        }
        composable("GroupPage/{id}") {
            val id = it.arguments!!.getString("id")!!.toInt()
            GroupPage(navController, id)
        }
        composable("PlayerPage/{groupID}/{playerID}") {
            val groupID = it.arguments!!.getString("groupID")!!.toInt()
            val playerID = it.arguments!!.getString("playerID")!!.toInt()
            PlayerPage(navController, groupID, playerID)
        }
        composable("StaffPage/{groupID}/{staffID}") {
            val groupID = it.arguments!!.getString("groupID")!!.toInt()
            val staffID = it.arguments!!.getString("staffID")!!.toInt()
            StaffPage(navController, groupID, staffID)
        }
    }
}





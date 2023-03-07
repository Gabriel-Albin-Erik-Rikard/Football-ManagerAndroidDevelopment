package com.example.football_manager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green),
    ){
        Text(text = "PROFILE")
    }
}

@Composable
fun ProfilePagePlayer(navController: NavController, groupID: Int, playerID: Int){
    println(groupID)
    println("kanske hit 3")
    //println(name)
    val player = groupRepository.getGroupById(groupID).getPlayerById(playerID)
    println("kanske hit 4")
    Box( modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 50.dp)
        //.background(Color.Blue)
    ) {
        // TODO import an Image here
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Favorite",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopCenter),
            tint = Color.Red
        )
        Card(
            //shape =
            //shape = RoundedCornerShape(12.dp),

            modifier = Modifier
                .height(300.dp)
                .align(Alignment.BottomCenter)
                .width(480.dp)
                //.padding(12.dp)
                .background(Color.LightGray)
        ) {
            Text(
                //text = "testing"
                text = "Name: ${player.name} \n" +
                        "Games Played: ${player.gamesPlayed} \n"+
                        "Goals: ${player.goals} \n"+
                        "Assists: ${player.assists} \n"+
                        "Yellow Cards: ${player.yellowCards} \n"+
                        "Red Cards: ${player.redCards}",
                style = typography.h4,
                //textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(6.dp)

            )
        }
    }
}


package com.example.football_manager

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

data class Staff( val id : Int, val name: String){
    var role: String = ""
}
data class Player(val id : Int, val name : String){
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
    /*fun addGroup(group: com.example.football_manager.Group) {
        val id = when {
            listOfGroups.isEmpty() -> 0
            else -> listOfGroups.last().id +1
        }
        listOfGroups.add(group)
    }*/

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
            println("kanske hit 0")
            val groupID = it.arguments!!.getString("groupID")!!.toInt()
            val playerID = it.arguments!!.getString("playerID")!!.toInt()
            println("kanske hit 1")
            PlayerPage(navController, groupID, playerID)
        }
        composable("StaffPage/{groupID}/{staffID}") {
            println("kanske hit 0")
            val groupID = it.arguments!!.getString("groupID")!!.toInt()
            val staffID = it.arguments!!.getString("staffID")!!.toInt()
            println("kanske hit 1")
            StaffPage(navController, groupID, staffID)
        }
    }
}

@Composable
fun GroupScreenStart(navController: NavController, groups : List<com.example.football_manager.Group>){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
        //.background(Color.Green),
    ) {
        LazyColumn(
            modifier = Modifier
                //.background(color = Color.Blue)
                .fillMaxSize(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)

        )
        {
            item {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                ) {
                    Text(text = "Create(implement later)")
                }
            }
            items(groups) { group ->
                Column(modifier = Modifier.padding(vertical = 15.dp)
                    .clickable { navController.navigate("GroupPage/${group.id}") }) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(150.dp),
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .width(1100.dp)
                    ) {
                        Text(
                            text = group.name,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 25.dp, vertical = 15.dp),
                            color = androidx.compose.ui.graphics.Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GroupPage(navController: NavController, id: Int){
    val group = groupRepository.getGroupById(id)
    Box(
        modifier = Modifier
            .padding(bottom = 50.dp)
    ){
        LazyColumn(
            modifier = Modifier
                //.background(color = Color.Blue)
                .padding()
                .fillMaxSize(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        )
        {
            item{
                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                )   {
                    Text(text = "Add(implement later,mby)")
                }
                androidx.compose.material3.Text(
                    text = "Staff",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .padding(10.dp)
                )
            }
            items(group.listOfStaff) { staff ->
                Column(modifier = Modifier.padding(vertical = 0.dp)
                    .clickable { navController.navigate("StaffPage/${group.id}/${staff.id}") }) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(150.dp),
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .width(1100.dp)
                    ) {
                        Text(
                            text = staff.name,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                            color = androidx.compose.ui.graphics.Color.White
                        )
                    }
                }
            }
            item{
                androidx.compose.material3.Text(
                    text = "Players",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .padding(10.dp)
                )
            }
            items(group.players) { player ->
                Column(modifier = Modifier.padding(vertical = 0.dp)
                    .clickable { navController.navigate("PlayerPage/${group.id}/${player.id}") }) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(150.dp),
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .width(1100.dp)
                    ) {
                        Text(
                            text = player.name,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                            color = androidx.compose.ui.graphics.Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerPage(navController: NavController, groupID: Int, playerID: Int){
    println(groupID)
    println("kanske hit 3")
    //println(name)
    val player = groupRepository.getGroupById(groupID).getPlayerById(playerID)
    println("kanske hit 4")
    Box( modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 50.dp)
        //.background(Color.Blue)
    ) {
        // TODO import an Image here
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Favorite",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopCenter),
            tint = Color.Red
        )
        Card(
            //shape =
            //shape = RoundedCornerShape(12.dp),

            modifier = Modifier
                .height(300.dp)
                .align(Alignment.BottomCenter)
                .width(480.dp)
                //.padding(12.dp)
                .background(Color.LightGray)
        ) {
            Text(
                //text = "testing"
                text = "Name: ${player.name} \n" +
                        "Games Played: ${player.gamesPlayed} \n"+
                        "Goals: ${player.goals} \n"+
                        "Assists: ${player.assists} \n"+
                        "Yellow Cards: ${player.yellowCards} \n"+
                        "Red Cards: ${player.redCards}",
                style = typography.h4,
                //textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(6.dp)

            )
        }
    }
}

@Composable
fun StaffPage(navController: NavController, groupID: Int, staffID: Int){
    println(groupID)
    println("kanske hit 3")
    //println(name)
    val staff = groupRepository.getGroupById(groupID).getStaffById(staffID)
    println("kanske hit 4")
    Box( modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 50.dp)
        //.background(Color.Blue)
    ) {
        // TODO import an Image here
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Favorite",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopCenter),
            tint = Color.Red
        )
        Card(
            //shape =
            //shape = RoundedCornerShape(12.dp),

            modifier = Modifier
                .height(300.dp)
                .align(Alignment.BottomCenter)
                .width(480.dp)
                //.padding(12.dp)
                .background(Color.LightGray)
        ) {
            Text(
                //text = "testing"
                text = "Name: ${staff.name} \n" +
                        "Role:  ${staff.role}",


                style = typography.h4,
                //textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(6.dp)

            )
        }
    }
}

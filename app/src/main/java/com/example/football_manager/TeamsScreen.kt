package com.example.football_manager

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.data.Group
import androidx.compose.ui.tooling.data.UiToolingDataApi
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

//TEST DATA
data class Group(var id: Int,
                 var name : String,
                 val players: MutableList<Player>
)
data class Player(
    var id : Int,
    var assists : Int,
    var yellowCards : Int,
    var gamesPlayed : Int,
    var name : String,
    var goals : Int,
    var redCards : Int
)

fun createTest() {
    val group1 = Group(1, "Team A", mutableListOf())
    val player1 = Player(1, 2, 2, 2, "Jöken", 2, 3)
    val player2 = Player(2, 3, 4, 4, "Boris", 4, 5)
    group1.players.add(player1)
    group1.players.add(player2)

    val group2 = Group(1, "Team A", mutableListOf())
    val player3 = Player(1, 2, 2, 2, "Dala", 2, 3)
    val player4 = Player(2, 3, 4, 4, "Hästen", 4, 5)
    group2.players.add(player3)
    group2.players.add(player4)

}


@Composable
fun TeamsScreen(){
    val groups = listOf(
        Group(1, "Team A", mutableListOf()),
        Group(2, "Team B", mutableListOf()),
        Group(3, "Team C", mutableListOf())
    )

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "GroupScreenStart"
    ){
        composable("GroupScreenStart") {
            GroupScreenStart(navController)
        }
        /*composable("GroupPage") {
            GroupPage(navController)
        }
        composable("PlayerPage/{id}") {
            val id = it.arguments?.getString("id")!!.toInt()
            PlayerPage(id,navController)
        }*/
    }
}

@Composable
fun GroupScreenStart(navController: NavController){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
            .background(Color.Green),
    ){
        LazyColumn(
        modifier = Modifier
            .background(color = Color.Blue)
            .fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)


    )
    {
        item{
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
            )   {
                Text(text = "Create(implement later)")
                }
            }
        //maybe use text here instead of buttons?
        //Gillar Card
            /*items(Groups){ group ->
                Card(
                    //shape =
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .height(80.dp)
                        .width(280.dp)
                        //.alignment = Alignment.Center
                        //.background(androidx.compose.ui.graphics.Color.LightGray)
                        .padding(12.dp)
                        .clickable {
                            //ta mig vidare här
                            navController.navigate("GroupPage")
                            println("hejhej")
                        }
                ) {
                    Text(
                        text = group.name,
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            //.padding(12.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }*/
    }
}

@Composable
fun GroupPage(navController: NavController){
    Box(
    modifier = Modifier
        //.fillMaxSize()
        .padding(bottom = 50.dp)
        .background(Color.Blue),
    ){
        LazyColumn(
            modifier = Modifier
                .background(color = Color.Blue)
                .padding()
                .fillMaxSize(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)


        )
        {
            item{
                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                )   {
                    Text(text = "Add(implement later,mby)")
                }
            }
            /*items(ListOfPlayers){player ->
                Card(
                    //shape =
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .height(80.dp)
                        .width(280.dp)
                        //.alignment = Alignment.Center
                        //.background(androidx.compose.ui.graphics.Color.LightGray)
                        .padding(12.dp)
                        .clickable {
                            //ta mig vidare här
                            navController.navigate("playerPage/${player.id}")
                        }
                ) {
                    Text(
                        text = player.name,
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            //.padding(12.dp)
                            .background(Color.LightGray)
                    )
                }*/
            }
        }
    }
}

@Composable
fun PlayerPage(id: Int ,navController: NavController){
    //val player = ListOfPlayers.find{it.id == id}?:return
    Box( modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 50.dp)
        .background(Color.Blue)
    ) {
        // TODO import an Image here
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Favorite",
            modifier = Modifier
                .size(400.dp)
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
                text = "testing"
                /*text = "Name: ${player.name} \n" +
                        "Games Played: ${player.gamesPlayed} \n"+
                        "Goals: ${player.goals} \n"+
                        "Assists: ${player.assists} \n"+
                        "Yellow Cards: ${player.yellowCards} \n"+
                        "Red Cards: ${player.redCards}",
                style = MaterialTheme.typography.h4,
                //textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(6.dp)*/

            )
        }
    }
}

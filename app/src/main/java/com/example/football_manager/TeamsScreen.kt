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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

//TEST DATA
data class gruop(
    var name : String,
)

data class player(
    var id : Int,
    var assists : Int,
    var yellowCards : Int,
    var gamesPlayed : Int,
    var name : String,
    var goals : Int,
    var redCards : Int
)

val ListOfPlayers = mutableListOf<player>()


@Composable
fun TeamsScreen(){
    ListOfPlayers.add(player(1,2,3,4,"Kroken",3,5))
    ListOfPlayers.add(player(2,2,3,4,"Dala",3,5))
    ListOfPlayers.add(player(3,2,3,4,"Jöken",3,5))
    ListOfPlayers.add(player(4,2,3,4,"Boris",3,5))
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "GroupScreenStart"
    ){
        composable("GroupScreenStart") {
            GroupScreenStart(navController)
        }
        composable("GroupPage") {
            GroupPage(navController)
        }
        composable("PlayerPage/{id}") {
            val id = it.arguments?.getString("id")!!.toInt()
            PlayerPage(id,navController)
        }
    }
}

@Composable
fun GroupScreenStart(navController: NavController){
    val Groups = mutableListOf<gruop>()
    Groups.add(gruop("SexySwingers"))
    Groups.add(gruop("SexySwingers"))
    Groups.add(gruop("Gruop of people who like really long names for their club even longer name"))
    Groups.add(gruop("SexySwingers"))
    Groups.add(gruop("SexySwingers"))
    Groups.add(gruop("SexySwingers"))
    Groups.add(gruop("SexySwingers"))
    Groups.add(gruop("SexySwingers"))
    Groups.add(gruop("SexySwingers"))
    Groups.add(gruop("SexySwingers"))
    Groups.add(gruop("SexySwingers"))
    Groups.add(gruop("SexySwingers"))

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
            items(Groups){ group ->
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
        }
    }
}

@Composable
fun GroupPage(navController: NavController){
    Box(
    modifier = Modifier
        //.fillMaxSize()
        .padding(bottom = 50.dp)
        .background(Color.Green),
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
            //maybe use text here instead of buttons?
            //Gillar Card
            items(ListOfPlayers){player ->
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
                }
            }
        }
    }
}

@Composable
fun PlayerPage(id: Int ,navController: NavController){
    val player = ListOfPlayers.find{it.id == id}?:return
    Box( modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 50.dp)
        .background(Color.Green)
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
            shape = RoundedCornerShape(12.dp),

            modifier = Modifier
                .height(400.dp)
                .align(Alignment.BottomCenter)
                .width(420.dp)
                .padding(12.dp)
                .background(Color.LightGray)
        ) {
            Text(
                text = "Name: ${player.name} \n" +
                        "Games Played: ${player.gamesPlayed} \n"+
                        "Goals: ${player.goals} \n"+
                        "Assists: ${player.assists} \n"+
                        "Yellow Cards: ${player.yellowCards} \n"+
                        "Red Cards: ${player.redCards}",
                style = MaterialTheme.typography.h4,
                //textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(12.dp)
                    .background(Color.LightGray)
            )
        }
    }
}

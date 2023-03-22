package com.example.football_manager.Groups

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.football_manager.Human
import com.example.football_manager.Player
import com.example.football_manager.Staff
import com.example.football_manager.model.Person
import com.example.football_manager.viewmodel.PersonViewModel

class ViewProfile {
}

//shows strings in a nice way on a card like this:
//str1                                  str2
@Composable
fun showCard(str1: String, str2: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Card(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(str1)
                Text(str2)
            }
        }
    }
}



//shows info about a human with nice profile picture att the top
@Composable
fun showProfile(human: Human){
    showCard(str1 = "yeye", str2 ="coool" )
    Column(

    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile picture
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .border(
                                2.dp,
                                androidx.compose.ui.graphics.Color.Black,
                                RoundedCornerShape(50.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        // TODO: Add profile picture
                        Text(
                            text = "Profile picture",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                    // Name
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                                text = human.name,
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center,
                        )
                    }
                    // Update and logout buttons, not used for now
                    /*Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        /* Button(
                             modifier = Modifier
                                 .height(60.dp)
                                 .width(150.dp),
                             onClick = { /*TODO*/ },
                             shape = RoundedCornerShape(8.dp),
                             elevation = ButtonDefaults.elevation(4.dp)
                         ) {
                             Text(
                                 text = "Update",
                                 style = MaterialTheme.typography.titleLarge,
                                 textAlign = TextAlign.Center,
                                 color = androidx.compose.ui.graphics.Color.White
                             )
                         }
                         Button(
                             modifier = Modifier
                                 .height(50.dp)
                                 .width(150.dp),
                             onClick = { /*TODO*/ },
                             shape = RoundedCornerShape(8.dp),
                             elevation = ButtonDefaults.elevation(4.dp)
                         ) {
                             Text(
                                 text = "Logout",
                                 style = MaterialTheme.typography.titleLarge,
                                 textAlign = TextAlign.Center,
                                 color = androidx.compose.ui.graphics.Color.White
                             )
                         }*/
                    }*/
                }
            }
            // Cards with user info
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (human is Staff) {
                showCard(str1 = "Role:", str2 = human.role)
            }
            else if (human is Player){
                showCard(str1 = "Goals:", str2 = human.goals.toString())
                showCard(str1 = "Assists:", str2 = human.assists.toString())
                showCard(str1 = "Games played:", str2 = human.gamesPlayed.toString())
                showCard(str1 = "Yellow cards:", str2 = human.yellowCards.toString())
                showCard(str1 = "Red cards:", str2 = human.redCards.toString())
            }
            else if (human is Person){
                showCard(str1 = "First Name:", str2 =human.firstName )
            }
            else{
                showCard(str1 = "Error", str2 ="Cant handle input" )
            }
        }
    }
}
package com.example.football_manager

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.football_manager.viewmodel.PersonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ProfileScreen() {

    var personViewModel = PersonViewModel()
     personViewModel.getPerson(1)

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
                            text = personViewModel.person.firstName + " " + personViewModel.person.lastName,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                        )
                    }
                    // Update and logout buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
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
                        }
                    }
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
                        Text("email: ")
                        Text(personViewModel.person.email)
                    }
                }
            }
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
                        Text("Phone ")
                        personViewModel.person.phoneNumber?.let { Text(it) }
                    }
                }
            }
            // TODO: Add more cards
        }
    }

}

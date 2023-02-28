package com.example.football_manager

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ProfileScreen() {
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
                            text = "Name",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
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
                        Text("mail.mail@mail.com")
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
                        Text("email: ")
                        Text("mail.mail@mail.com")
                    }
                }
            }
            // TODO: Add more cards
        }
    }
}
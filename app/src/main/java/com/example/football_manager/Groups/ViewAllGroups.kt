package com.example.football_manager.Groups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.football_manager.model.PersonTeams

@Composable
fun GroupScreenStart(navController: NavController, groups : PersonTeams){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
            .padding(top = 30.dp)
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

            items(groups.staffTeams!!) { group ->
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
                            text = group.teamName!!,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 25.dp, vertical = 15.dp),
                            color = Color.White
                        )
                    }
                }
            }
            items(groups.playerTeams!!) { group ->
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
                            text = group.teamName!!,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 25.dp, vertical = 15.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

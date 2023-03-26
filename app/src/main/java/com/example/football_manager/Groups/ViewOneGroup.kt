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
import com.example.football_manager.groupRepository
import com.example.football_manager.model.Person
import com.example.football_manager.viewmodel.TeamViewModel

@Composable
fun GroupPage(navController: NavController, teamViewModel: TeamViewModel){
    //val group = groupRepository.getGroupById(id)
    Box(
        modifier = Modifier
            .padding(bottom = 50.dp)
            .padding(top = 30.dp)
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
                Text(
                    text = "Staff",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .padding(10.dp)
                )
            }
            items(teamViewModel.staff!!) { staff ->
                Column(modifier = Modifier.padding(vertical = 0.dp)
                    .clickable { navController.navigate("StaffPage/${staff.id}") }) {
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
                            color = Color.White
                        )
                    }
                }
            }
            item{
                Text(
                    text = "Players",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .padding(10.dp)
                )
            }
            items(teamViewModel.players!!) { player ->
                Column(modifier = Modifier.padding(vertical = 0.dp)
                    .clickable { navController.navigate("PlayerPage//${player.id}") }) {
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
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

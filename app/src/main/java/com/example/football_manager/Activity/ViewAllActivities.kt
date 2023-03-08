package com.example.football_manager.Activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.football_manager.Activities
import kotlin.math.ceil
import kotlin.math.min

@Composable
fun ViewAllScreen(
    navController: NavHostController,
    listy: List<Activities>,
    itemsPerPage: Int = 3
) {
    val sortedList = listy.sortedBy { it.date }
    val pageCount = ceil(sortedList.size.toFloat() / itemsPerPage).toInt()
    var currentPage by remember { mutableStateOf(1) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Button(
                    onClick = {
                        navController.navigate("createActivity")
                    },
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Create Activity",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }

            val startIndex = (currentPage - 1) * itemsPerPage
            val endIndex = min(startIndex + itemsPerPage, listy.size)
            val sublist = sortedList.subList(startIndex, endIndex)

            items(sublist) { activities ->
                Surface(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(150.dp),
                    modifier = Modifier
                        .padding(horizontal = 100.dp, vertical = 15.dp)
                        .width(1100.dp)
                        .height(150.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { navController.navigate("viewOne/${activities.id}") },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally // Changed from Alignment.Start
                    ) {
                        Text(
                            text = activities.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )


                        Text(
                            text = activities.date,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${activities.startTime} - ${activities.finishTime}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }

                }
            }


            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { if (currentPage > 1) currentPage-- },
                        enabled = currentPage > 1
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Previous Page"
                        )
                    }
                    Text(
                        text = "Page $currentPage of $pageCount",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    IconButton(
                        onClick = { if (currentPage < pageCount) currentPage++ },
                        enabled = currentPage < pageCount
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next Page"
                        )

                    }
                }
            }
        }
    }
}

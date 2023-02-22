package com.example.football_manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.football_manager.ui.theme.FootballmanagerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FootballmanagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                    }
                }
            }
        }

}

@Composable
fun MyUI() {
    Box(modifier = Modifier.fillMaxSize()){
        BottomNavigation(
            modifier = Modifier.align(Alignment.BottomCenter)
        ){

        }
    }
}

//@Composable


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FootballmanagerTheme {
        //Greeting("Android")
    }
}
package com.example.football_manager

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.football_manager.ui.theme.FootballmanagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create shared preferences
        context = applicationContext

        val sharedPref = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()

        if (sharedPref != null) {
            sharedPref.edit().putInt("id", 1).apply()
        }

        setContent {
            FootballmanagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen()
                    }
                }
            }
        }

    companion object {
        var context: Context? = null
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
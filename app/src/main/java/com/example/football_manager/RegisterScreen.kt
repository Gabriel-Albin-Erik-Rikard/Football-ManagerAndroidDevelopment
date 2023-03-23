package com.example.football_manager

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.football_manager.network.FootballManagerAPIService


@Composable
fun RegisterScreen(navController: NavHostController) {
    // State for the username/email and password text fields. CHECKED: Does not cahnge if user tilts phone.
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val confirmEmailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }
    val confirmPasswordState = remember { mutableStateOf(TextFieldValue()) }

    // Check if the email and confirm email fields match
    val emailMatch = emailState.value.text == confirmEmailState.value.text
    val passwordMatch = passwordState.value.text == confirmPasswordState.value.text

    val snackbarVisibleState = remember { mutableStateOf(false) }
    val registerSuccessfulState = remember { mutableStateOf(false) }

    fun register(email: String, password: String) {
        var errorCode: String by mutableStateOf("")
        try {
            // Send register request to database.
            //FootballManagerAPIService.getInstance().register(RegisterRequest(email, password)) //TODO Needs to be set up on backend.
            registerSuccessfulState.value = true
        } catch (e: Exception) {
            errorCode = e.message.toString()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Email Text Field
        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        // CONFIRM Email Text Field
        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Confirm Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Error message if the email fields don't match
        if (!emailMatch) {
            Text(
                text = "Email does not match!",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Password text field
        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        //Confirm password state
        OutlinedTextField(
            value = confirmPasswordState.value,
            onValueChange = { confirmPasswordState.value = it },
            label = { Text("Confirm Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Error message if the email fields don't match
        if (!passwordMatch) {
            Text(
                text = "Passwords must match!",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }


        Button(
            onClick = {
                val email = emailState.value.text
                val password = passwordState.value.text
                register(email,password)
            },
            enabled = emailMatch,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Register")
        }
        if (registerSuccessfulState.value) {
            Snackbar(
                action = {
                    Button(onClick = { registerSuccessfulState.value = false }) {
                        Text("Dismiss")
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Register sent!")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(navController = rememberNavController())
}

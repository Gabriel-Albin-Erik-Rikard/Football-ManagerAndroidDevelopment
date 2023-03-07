package com.example.football_manager

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInOptions




@Composable
fun LoginScreen(
    navController: NavHostController,
) {
    // State for the username/email and password text fields. Makes sure the values entered remain even if, for example, the user tilts their phone.
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }
    var showLoginOverlay by remember { mutableStateOf(true) }   //Show the login-screen as an overlay

    if (!isUserLoggedIn()) {
        if (showLoginOverlay) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Username/email text field
                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text("Username/Email") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // Password text field
                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // Login button
                Button(
                    onClick = {
                        // Handle login logic here
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Login")
                }

                // Register button that navigates to RegisterScreen
                TextButton(
                    onClick = { navController.navigate("RegisterScreen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Register")
                }

            }
            }
        else
            navController.navigate("HomeScreen")
        }
    }


// Check if user is logged in. TODO
fun isUserLoggedIn(): Boolean {
    return false
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
            LoginScreen(navController = rememberNavController())
}


// Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestEmail()
    .build()



//SHA1 Fingerprint
//SHA1: EF:FC:04:9C:CE:55:9F:C4:3F:80:DD:46:37:A2:C8:40:DB:FB:8C:02


/*
val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestEmail()
    .build()

val googleSignInClient = GoogleSignIn.getClient(context, gso)

// Call this function when the user clicks the Gmail login button
fun signInWithGmail() {
    val signInIntent = googleSignInClient.signInIntent
    startActivityForResult(signInIntent, RC_SIGN_IN)
}

// Call this function when the user clicks the Facebook login button
fun signInWithFacebook() {
    LoginManager.getInstance().logInWithReadPermissions(activity, listOf("public_profile", "email"))
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == RC_SIGN_IN) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        handleGmailSignInResult(task)
    }
}

fun handleGmailSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
        val account = completedTask.getResult(ApiException::class.java)
        // Signed in successfully, show authenticated UI.
        updateUI(account)
    } catch (e: ApiException) {
        // The ApiException status code indicates the detailed failure reason.
        Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        updateUI(null)
    }
}

fun updateUI(account: GoogleSignInAccount?) {
    if (account != null) {
        // User signed in, show authenticated UI.
    } else {
        // User is not signed in, show unauthenticated UI.
    }
}

fun updateUI(token: AccessToken?) {
    if (token != null) {

*/
package com.example.football_manager

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ericampire.mobile.firebaseauthcompose.ui.login.LoginScreenViewModel
import com.example.football_manager.network.FootballManagerAPIService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private var mAuth: FirebaseAuth? = null
@Composable
fun RegisterScreen() {
    // State for the username/email and password text fields. CHECKED: Does not cahnge if user tilts phone.
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val confirmEmailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }
    val confirmPasswordState = remember { mutableStateOf(TextFieldValue()) }

    // Check if the email and confirm email fields match
    val emailMatch = emailState.value.text == confirmEmailState.value.text
    val passwordMatch = passwordState.value.text == confirmPasswordState.value.text

    //For firebase
    val context = LocalContext.current
    mAuth = FirebaseAuth.getInstance()
    val viewModel: LoginScreenViewModel = viewModel()

    val registerSuccessfulState = remember { mutableStateOf(false) }


    //UI
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
            value = confirmEmailState.value,
            onValueChange = { confirmEmailState.value = it },
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

        // Error message if the password fields don't match
        if (!passwordMatch && confirmPasswordState.value != null && passwordState.value != null) {
            Text(
                text = "Passwords must match!",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        fun register(email: String, password: String) {
            var errorCode: String by mutableStateOf("")
            try {
                // Send register request to database.
                FootballManagerAPIService.getInstance().register(RegisterRequest(email, password)) //TODO Needs to be set up on backend.
                registerSuccessfulState.value = true
            } catch (e: Exception) {
                errorCode = e.message.toString()
            }
        }


        Button(
            onClick = {
                val email = emailState.value.text
                val password = passwordState.value.text
                register(email, password)
            },
            enabled = emailMatch,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Register")
        }


        //Google Register
        val context = LocalContext.current
        val token = stringResource(R.string.web_client_id)
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                    viewModel.signWithCredential(credential)

                    Firebase.auth.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = mAuth!!.getCurrentUser()!!
                            val gmailId = firebaseUser.uid
                            FootballManagerAPIService.getInstance().gmailRegister(RegisterRequest(gmailId)) //TODO Needs to be set up on backend.
                            Toast.makeText(
                                context,
                                "User Registered Successfully",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            //  Toast.makeText(context, gmailId, Toast.LENGTH_LONG).show()

                        } else {
                            Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_LONG).show()
                            // UpdateUI(null)
                        }
                    }
                } catch (e: ApiException) {
                    Log.w("TAG", "Google sign in failed", e)
                }

            }
        OutlinedButton(
            border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            },
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Icon(
                            tint = Color.Unspecified,
                            painter = painterResource(id = R.drawable.ic_google_logo),
                            contentDescription = null,
                        )
                        Text(
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.onSurface,
                            text = "Google Register"
                        )
                        Icon(
                            tint = Color.Transparent,
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = null,
                        )
                    }
                )
            }
        )

    }
    if (registerSuccessfulState.value) {
        Snackbar(
            action = {},
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Register sent!", textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    LoginScreen()
}

package com.example.football_manager

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ericampire.mobile.firebaseauthcompose.ui.login.LoginScreenViewModel
import com.ericampire.mobile.firebaseauthcompose.ui.login.LoginViewModel
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

    val firstNameState = remember { mutableStateOf(TextFieldValue()) }
    val lastNameState = remember { mutableStateOf(TextFieldValue()) }
    val firebaseId = remember { mutableStateOf("") }

    // Check if the email and confirm email fields match
    val emailMatch = emailState.value.text == confirmEmailState.value.text
    val passwordMatch = passwordState.value.text == confirmPasswordState.value.text

    //For firebase
    val context = LocalContext.current
    mAuth = FirebaseAuth.getInstance()
    val viewModel: LoginScreenViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()

    val userLoggedIn = loginViewModel.userLoggedIn.value

    val sharedPre = context.getSharedPreferences("myPref", Context.MODE_PRIVATE)

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
        if (!emailMatch && emailMatch != null && confirmEmailState != null) {
            Text(
                text = "Email does not match and cannot be empty!",
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
                text = "Passwords must match and cannot be empty!",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // First name text field

        OutlinedTextField(
            value = firstNameState.value,
            onValueChange = { firstNameState.value = it },
            label = { Text("First Name") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Last name text field

        OutlinedTextField(
            value = lastNameState.value,
            onValueChange = { lastNameState.value = it },
            label = { Text("Last Name") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        fun register(email: String, password: String, firstName: String, lastName: String) {
            var errorCode: String by mutableStateOf("")
            try {
                loginViewModel.signUpWithEmailAndPassword(email, password, firstName, lastName, sharedPre)
                registerSuccessfulState.value = true
            } catch (e: Exception) {
                errorCode = e.message.toString()
            }
        }


        Button(
            onClick = {
                val email = emailState.value.text
                val password = passwordState.value.text
                val firstName = firstNameState.value.text
                val lastName = lastNameState.value.text
                register(email, password,firstName, lastName)
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

                            // Get first and last name from google account
                            val personName = account.displayName

                            val firstName = personName?.split(" ")?.get(0)
                            val lastName = personName?.split(" ")?.get(1)
                            val email = account.email

                            if (firstName != null && lastName != null && email != null) {
                                loginViewModel.signUpWithEmailAndPassword(email, gmailId, firstName, lastName, sharedPre, firebaseId = gmailId)
                                registerSuccessfulState.value = true
                            }

                            // FootballManagerAPIService.getInstance().gmailRegister(RegisterRequest(gmailId)) //TODO Needs to be set up on backend.
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
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    LoginScreen()
}

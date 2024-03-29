package com.example.football_manager

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ericampire.mobile.firebaseauthcompose.ui.login.LoginScreenViewModel
import com.ericampire.mobile.firebaseauthcompose.ui.login.LoginViewModel
import com.example.football_manager.util.RegisterScreenActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



private const val TAG = "SignInWithGoogle"

lateinit var oneTapClient: SignInClient
lateinit var signInRequest: BeginSignInRequest
private var mAuth: FirebaseAuth? = null

@Composable
fun LoginScreen() {
    //var userLoggedIn by remember { mutableStateOf(false) }
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    mAuth = FirebaseAuth.getInstance()
    val viewModel: LoginScreenViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val userLoggedIn = loginViewModel.userLoggedIn.value

    val sharedPre = context.getSharedPreferences("myPref", Context.MODE_PRIVATE)
    val editor = sharedPre.edit()

    //If user is not logged in, open Login screen.
    if (!userLoggedIn) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_footman_launcher_playstore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 200.dp),
                elevation = 8.dp,
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
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
                                val email = emailState.value.text
                                val password = passwordState.value.text
                                loginViewModel.loginWithEmailAndPassword(email, password, sharedPre)

                                // If login is successful, navigate to HomeScreen

                                if (loginViewModel.userLoggedIn.value) {
                                    context.startActivity(
                                        Intent(
                                            context,
                                            MainActivity()::class.java
                                        )
                                    )
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Login failed. Please try again.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Login")
                        }

                        // Register button that navigates to RegisterScreen
                        Button(
                            onClick = {
                                context.startActivity(
                                    Intent(
                                        context,
                                        RegisterScreenActivity::class.java
                                    )
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Register")
                        }

                        //Login with google.
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

                                            val mail = firebaseUser.email

                                            loginViewModel.loginWithEmailAndPassword(mail!!, gmailId, sharedPre)

                                            Toast.makeText(context, "Sign in Successful", Toast.LENGTH_LONG).show()
                                            Toast.makeText(context, gmailId, Toast.LENGTH_LONG).show()

                                            //userLoggedIn = true

                                        } else {
                                            Toast.makeText(context, "Sign in Failed", Toast.LENGTH_LONG).show()
                                            // UpdateUI(null)
                                        }
                                    }


                                } catch (e: ApiException) {
                                    Log.w("TAG", "Google sign in failed", e)
                                }
                            }


                        val context = LocalContext.current
                        val token = stringResource(R.string.web_client_id)
                        OutlinedButton(
                            border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            onClick = {
                                val gso =
                                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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
                                            text = "Google"
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

                        // Skip login button. TODO USE ONLY FOR TESTING! 
                        TextButton(
                            onClick = {
                                //userLoggedIn = true

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text("Skip Login")
                        }

                    }
                }
            }
        }
    }
        else
        MainScreen()
    }

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}

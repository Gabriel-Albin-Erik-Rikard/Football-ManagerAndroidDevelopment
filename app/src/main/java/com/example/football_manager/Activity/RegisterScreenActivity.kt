package com.example.football_manager.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ericampire.mobile.firebaseauthcompose.ui.login.LoginScreenViewModel
import com.example.football_manager.R
import com.example.football_manager.ui.theme.FootballmanagerTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FootballmanagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RegisterScreen()
                }
            }
        }
    }
}
private var mAuth: FirebaseAuth? = null
@Composable
fun RegisterScreen() {
    var userLoggedIn by remember { mutableStateOf(false) }
    // State for the username/email and password text fields. Makes sure the values entered remain even if, for example, the user tilts their phone.
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val confirmEmailState = remember { mutableStateOf(TextFieldValue()) }

    val passwordState = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    mAuth = FirebaseAuth.getInstance()
    val viewModel: LoginScreenViewModel = viewModel()

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            viewModel.signWithCredential(credential)

            Firebase.auth.signInWithCredential(credential).addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    val firebaseUser: FirebaseUser = mAuth!!.getCurrentUser()!!
                    val gmailId=firebaseUser.uid

                    Toast.makeText(context, "User Registered Successfully", Toast.LENGTH_LONG).show()
                  //  Toast.makeText(context, gmailId, Toast.LENGTH_LONG).show()

                    userLoggedIn = true

                }else {
                    Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_LONG).show()
                    // UpdateUI(null)
                }
            }




        } catch (e: ApiException) {
            Log.w("TAG", "Google sign in failed", e)
        }
    }

    // Check if the email and confirm email fields match
    val emailMatch = emailState.value.text == confirmEmailState.value.text

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

        // Confirm Register
        Button(
            onClick = {
            },
            enabled = emailMatch,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Register")
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

    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    FootballmanagerTheme {
        RegisterScreen()
    }
}
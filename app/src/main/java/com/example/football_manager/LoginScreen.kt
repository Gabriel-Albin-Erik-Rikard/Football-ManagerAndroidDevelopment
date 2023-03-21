package com.example.football_manager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.ericampire.mobile.firebaseauthcompose.ui.login.LoginScreenViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


private const val RC_SIGN_IN = 123 //Sign in value
private const val REQ_ONE_TAP = 456
private const val TAG = "SignInWithGoogle"

lateinit var oneTapClient: SignInClient
lateinit var signInRequest: BeginSignInRequest
private var mAuth: FirebaseAuth? = null

    @Composable
    fun LoginScreen() {
        var userLoggedIn by remember { mutableStateOf(false) }
        val emailState = remember { mutableStateOf(TextFieldValue()) }
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

                        Toast.makeText(context, "Sign in Successful", Toast.LENGTH_LONG).show()
                        Toast.makeText(context, gmailId, Toast.LENGTH_LONG).show()

                        userLoggedIn = true

                    }else {
                        Toast.makeText(context, "Sign in Failed", Toast.LENGTH_LONG).show()
                        // UpdateUI(null)
                    }
                }




            } catch (e: ApiException) {
                Log.w("TAG", "Google sign in failed", e)
            }
        }




        if (!userLoggedIn) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
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
                            onClick = {
                                context.startActivity(Intent(context, RegisterScreenActivity::class.java))
                                      },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Register")
                        }


//                        Button(
//                            onClick = {
//                                oneTapClient.beginSignIn(signInRequest)
//                                    .addOnSuccessListener(this) { result ->
//                                        try {
//                                            startIntentSenderForResult(
//                                                result.pendingIntent.intentSender, REQ_ONE_TAP,
//                                                null, 0, 0, 0, null)
//                                        } catch (e: IntentSender.SendIntentException) {
//                                            Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
//                                        }
//                                    }
//                                    .addOnFailureListener(this) { e ->
//                                        // No saved credentials found. Launch the One Tap sign-up flow, or
//                                        // do nothing and continue presenting the signed-out UI.
//                                        Log.d(TAG, e.localizedMessage)
//                                    }
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp, vertical = 8.dp)
//                        ) {
//                            Text("Sign in with Google")
//                        }


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


                        // Skip login button. TODO Should this be possible!?
                        TextButton(
                            onClick = {
                                userLoggedIn = true

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
        } else
            MainScreen()
    }


    // Check if user is logged in. TODO
    fun isUserLoggedIn(): Boolean {
        return false
    }





@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()

}



class GoogleAccountCreate : AppCompatActivity() {
    class YourActivity : AppCompatActivity() {
        // ...

        private lateinit var oneTapClient: SignInClient
        private lateinit var signUpRequest: BeginSignInRequest

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            //Google Login Layout
            val loginLayout = LinearLayout(this)
            loginLayout.orientation = LinearLayout.VERTICAL
            loginLayout.gravity = Gravity.CENTER

            oneTapClient = Identity.getSignInClient(this)
            signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.web_client_id))
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                )
                .build()
            // ...
        }
        // ...
    }


    class GoogleSignIn : AppCompatActivity() {
        // ...
        // ...

        private lateinit var oneTapClient: SignInClient
        private lateinit var signInRequest: BeginSignInRequest

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            //Google Login Layout
            val loginLayout = LinearLayout(this)
            loginLayout.orientation = LinearLayout.VERTICAL
            loginLayout.gravity = Gravity.CENTER

            oneTapClient = Identity.getSignInClient(this)
            signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(
                    BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build()
                )
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build()
                )
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build()
            // ...
        }
        // ...
    }
}

class YourActivity : AppCompatActivity() {

    // ...
    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true
    // ...

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    val username = credential.id
                    val password = credential.password
                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with your backend.
                            Log.d(TAG, "Got ID token.")
                        }
                        password != null -> {
                            // Got a saved username and password. Use them to authenticate
                            // with your backend.
                            Log.d(TAG, "Got password.")
                        }
                        else -> {
                            // Shouldn't happen.
                            Log.d(TAG, "No ID token or password!")
                        }
                    }
                } catch (e: ApiException) {
                    // ...
                }
            }
        }
    }
    // ...
}

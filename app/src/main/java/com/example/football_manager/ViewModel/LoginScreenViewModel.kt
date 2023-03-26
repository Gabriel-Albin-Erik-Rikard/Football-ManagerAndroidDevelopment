package com.ericampire.mobile.firebaseauthcompose.ui.login

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericampire.mobile.firebaseauthcompose.util.LoadingState
import com.example.football_manager.MainActivity
import com.example.football_manager.network.FootballManagerAPIService
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

//Viewmodel for login GOOGLE AND FIREBASE!
class LoginScreenViewModel : ViewModel() {

  val loadingState = MutableStateFlow(LoadingState.IDLE)

  fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
    try {
      loadingState.emit(LoadingState.LOADING)
      Firebase.auth.signInWithEmailAndPassword(email, password).await()
      loadingState.emit(LoadingState.LOADED)
    } catch (e: Exception) {
      loadingState.emit(LoadingState.error(e.localizedMessage))
    }
  }

  fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
    try {
      loadingState.emit(LoadingState.LOADING)
      Firebase.auth.signInWithCredential(credential).await()
      loadingState.emit(LoadingState.LOADED)
    } catch (e: Exception) {
      loadingState.emit(LoadingState.error(e.localizedMessage))
    }
  }
}

//Viewmodel for login. Can probably be merged with the "Person" model, but functional for now.
class LoginViewModel : ViewModel() {
  var userLoggedIn = mutableStateOf(false)
  var errorCode: String by mutableStateOf("")

  fun loginWithEmailAndPassword(email: String, password: String, sharedPre: SharedPreferences) {
    viewModelScope.launch {
      try {
        // Make a POST request to the login endpoint with email and password
        val response =
          FootballManagerAPIService.getInstance().loginWithEmailAndPassword(email, password)
        // Retrieve the ID from the response

        val id = response.id
        val isCoach = response.isStaff
        val JWT = response.JWT

        println("Person ID")
        println(id)

        // Set the userLoggedIn flag to true
        if (response.loggedIn == true) {
          // Save the ID to shared preferences

          println("Saving id: $id to shared preferences")

          sharedPre.edit().putInt("id", id).apply()
          if (isCoach != null) {
            sharedPre.edit().putBoolean("isCoach", isCoach).apply()
          }
            sharedPre.edit().putString("JWT", JWT).apply()

          userLoggedIn.value = true

        }
      } catch (e: Exception) {
        errorCode = e.message.toString()
      }
    }
  }

  fun signUpWithEmailAndPassword(
    email: String,
    password: String,
    firstName: String,
    lastName: String,
    sharedPre: SharedPreferences,
    firebaseId: String? = null
  ) {

    viewModelScope.launch {
      try {
        // Make a POST request to the login endpoint with email and password
        // :)
        val response =
          FootballManagerAPIService.getInstance().signUpWithEmailAndPassword(email, password, firstName, lastName, firebaseId)
        // Retrieve the ID from the response

        val id = response.id
        val isCoach = response.isStaff
        val JWT = response.JWT

        // Set the userLoggedIn flag to true
        if (response.loggedIn == true) {
          // Save the ID to shared preferences

          println("Saving id: $id to shared preferences")

          sharedPre.edit().putInt("id", id).apply()
          if (isCoach != null) {
            sharedPre.edit().putBoolean("isCoach", isCoach).apply()
          }
          sharedPre.edit().putString("JWT", JWT).apply()

          userLoggedIn.value = true

        }
      } catch (e: Exception) {
        errorCode = e.message.toString()
      }
    }
  }
}

/* //Old login code.
  fun login(email: String, password: String) {
    viewModelScope.launch {
      try {
        // Make a POST request to the login endpoint with email and password
       //  val response = FootballManagerAPIService.getInstance().login(LoginRequest(email, password)) //TODO Need backend to get data
        // Retrieve the ID from the response

       //  val id = response.id
       //  val isCoach = response.isCoach

        // Set the userLoggedIn flag to true
        userLoggedIn.value = true
      } catch (e: Exception) {
        errorCode = e.message.toString()
      }
    }
  }
}

 */
package com.example.football_manager


import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.football_manager.Activity.CreateActivity
import com.example.football_manager.Activity.EditActivity
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@Composable
fun QRScreen() {
    val navController = rememberNavController()



}



@Composable
fun CalenderScreen(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "viewAll"
    ) {
        composable("viewAll") {
            com.example.football_manager.Activity.ViewAllScreen(navController, activityRepository.getAllActivities())
        }
        composable("viewOne/{id}") {
            val id = it.arguments!!.getString("id")!!.toInt()
            com.example.football_manager.Activity.ViewOneScreen(id, navController)
        }

        composable("viewOneEditedActivity/{id}"){
            val id  = it.arguments!!.getString("id")!!.toInt()
            EditActivity(id, navController)

        }
        composable("createActivity") {
            CreateActivity(navController = navController)
        }
    }
}
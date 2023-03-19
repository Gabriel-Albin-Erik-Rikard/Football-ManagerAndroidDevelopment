package com.example.football_manager.QR

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ScanQrViewModel : ViewModel() {
    val isCameraPermissionGranted = mutableStateOf(true)

}


@Composable
fun ScanQR(scanQrViewModel: ScanQrViewModel){
    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
        scanQrViewModel.isCameraPermissionGranted.value = isGranted
    }
    val context = LocalContext.current as Activity
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val analysisUseCase: ImageAnalysis = ImageAnalysis.Builder()
        .build()
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE
        )
        .build()
    val scanner = BarcodeScanning.getClient(options)

    fun requestCameraPermission(){
        when {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                scanQrViewModel.isCameraPermissionGranted.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                context,
                android.Manifest.permission.CAMERA
            ) -> scanQrViewModel.isCameraPermissionGranted.value = true

            else -> requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy,
        cameraProvider: ProcessCameraProvider
    ) {
        imageProxy.image?.let { image ->
            val inputImage = InputImage.fromMediaImage(
                image,
                imageProxy.imageInfo.rotationDegrees
            )
            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodeList ->
                    val barcode = barcodeList.getOrNull(0)

                    barcode?.rawValue?.let { value ->
                        cameraProvider.unbindAll()
                        Toast.makeText(context, value, Toast.LENGTH_LONG).show()
                        // Delay the re-binding of the camera by 5 seconds
                        Handler(Looper.getMainLooper()).postDelayed({
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                analysisUseCase
                            )
                        }, 5000)
                    }
                }
                .addOnFailureListener {}
                .addOnCompleteListener {
                    imageProxy.image?.close()
                    imageProxy.close()
                }
        }
    }



    LaunchedEffect(key1 = true ) {
        requestCameraPermission()
    }

    suspend fun Context.getCameraProvider(): ProcessCameraProvider =
        suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(this).also { cameraProvider ->
                cameraProvider.addListener({
                    continuation.resume(cameraProvider.get())
                }, ContextCompat.getMainExecutor(this))
            }
        }

    LaunchedEffect(key1 = scanQrViewModel.isCameraPermissionGranted.value){
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            analysisUseCase
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)

        analysisUseCase.setAnalyzer(Executors.newSingleThreadExecutor()){ imageProxy ->
            processImageProxy(scanner, imageProxy, cameraProvider)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    if (scanQrViewModel.isCameraPermissionGranted.value) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
    } else {
        Text(text = "Permission not Granted")
    }

}

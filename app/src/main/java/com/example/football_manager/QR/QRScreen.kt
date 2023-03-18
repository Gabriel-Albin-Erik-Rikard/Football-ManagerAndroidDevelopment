package com.example.football_manager


import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


@Composable
fun QRScreen() {
    QRGenerator()
}
@Composable
fun QRGenerator() {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .wrapContentHeight()
    ) {
        Text(
            text = "Want To Become A Football-Coach?",
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .align(Alignment.Center)

        )
        AndroidView(
            factory = { ctx ->
                ImageView(ctx).apply {
                    val size = 256
                    val hints = hashMapOf<EncodeHintType, Int>().also {
                        it[EncodeHintType.MARGIN] = 1
                    }
                    val url = "https://utbildning.sisuforlag.se/fotboll/tranare/tranarutbildning/"
                    val bits = QRCodeWriter().encode(
                        url,
                        BarcodeFormat.QR_CODE,
                        size,
                        size,
                        hints
                    )
                    val bitmap =
                        Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
                            for (x in 0 until size) {
                                for (y in 0 until size) {
                                    it.setPixel(
                                        x,
                                        y,
                                        if (bits[x, y]) Color.Black.toArgb() else Color.White.toArgb()
                                    )
                                }
                            }
                        }
                    setImageBitmap(bitmap)
                }
            }, modifier = Modifier
                .width(250.dp)
                .height(1500.dp)
               .padding(top = 300.dp)
        )
    }
}


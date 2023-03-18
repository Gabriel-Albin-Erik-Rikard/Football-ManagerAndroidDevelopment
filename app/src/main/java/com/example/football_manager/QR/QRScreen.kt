package com.example.football_manager


import android.graphics.Bitmap
import android.widget.ImageView
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

@Composable
fun QRScreen() {
    AndroidView(factory = { ctx ->
        ImageView(ctx).apply {
            val size = 512
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
        .width(width = 100.dp)
        .height(height = 100.dp)
    )
}

package com.example.basemvvmcleanandroid.screens.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import timber.log.Timber
import kotlin.ranges.until
import kotlin.text.isNullOrEmpty
import androidx.core.graphics.set
import androidx.core.graphics.createBitmap


@Composable
fun QrCodeView(modifier: Modifier = Modifier, qrCode : String?) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (!qrCode.isNullOrEmpty()) {
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(qrCode, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = createBitmap(width, height, Bitmap.Config.RGB_565)
            try {
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bmp[x, y] = (if (bitMatrix[x, y]) Color.Black else Color.White).toArgb()
                    }
                }

            } catch (e: Exception) {
                Timber.tag("TAG").v("QRCodeDialog : ${e.message}")
            }
            Image(
                painter = rememberAsyncImagePainter(model = bmp),
                contentDescription = null,
                modifier = Modifier
                    .size(282.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
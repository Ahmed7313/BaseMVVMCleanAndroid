package apps.max.cash.screens.home.home.qrcode

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.compose.ui.graphics.Color

import com.example.basemvvmcleanandroid.R

import apps.max.cash.widgets.qrcode.QrCodeGenerateImage
import com.daman.edman.screens.components.HeaderText
import com.example.basemvvmcleanandroid.ui.theme.RedColor
import com.example.basemvvmcleanandroid.ui.theme.lightBlue
import com.trend.thecontent.screens.components.MainButton
import kotlinx.coroutines.delay
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@SuppressLint("SdCardPath")
@Composable
fun QrCodeContentDialog(modifier: Modifier = Modifier, image: String, dismiss: () -> Unit) {
    val context = LocalContext.current
    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val composableView = remember { ComposeView(context) }

    return Box(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 20.dp)
                .background(lightBlue, shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Image(painter = painterResource(xyz.hasnat.sweettoast.R.drawable.ic_close),
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(24.dp)
                        .align(Alignment.TopEnd)


                        .clickable {
                            dismiss()
                        }, contentDescription = "qrcode"
                )
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            QrCodeGenerateImage(
                modifier = Modifier.size(150.dp),
                content = image,
                size = 250.dp
            ){
                bitmap = it
            }
            Spacer(modifier = Modifier.height(32.dp))
            HeaderText(
                text = stringResource(R.string.scan_tha_code),
                fontSize = 18,
                color = RedColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            MainButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = stringResource(R.string.share)
            ) {
//                bitmap?.let {
//                    shareBitmap(context, it)
//                }

            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
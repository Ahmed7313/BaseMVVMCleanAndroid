package apps.max.cash.widgets.qrcode

import android.graphics.Bitmap
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.graphics.createBitmap


@Composable
fun QrCodeGenerateImage(
    modifier: Modifier = Modifier,
    content: String,
    size: Dp,
    finished: (result: Bitmap) -> Unit
) {
    Box(
        modifier = modifier
            .size(size)
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        // QR Code Image
        val bitmap = rememberQrBitmap(content = content, size = size)

        if (bitmap != null) {
            Image(
                painter = remember(bitmap) { BitmapPainter(bitmap.asImageBitmap()) },
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(size),
            )
            finished(bitmap)
        } else {
            CircularProgressIndicator()
        }
    }
}

/** Taken from: https://gist.github.com/dev-niiaddy/8f936062291e3d328c7d10bb644273d0 */
@Composable
private fun rememberQrBitmap(content: String, size: Dp): Bitmap? {
    val density = LocalDensity.current
    val sizePx = with(density) { size.roundToPx() }

    var bitmap by remember(content) {
        mutableStateOf<Bitmap?>(null)
    }

    LaunchedEffect(bitmap) {
        if (bitmap != null) return@LaunchedEffect

        launch(Dispatchers.IO) {
            val qrCodeWriter = QRCodeWriter()

            val encodeHints = mutableMapOf<EncodeHintType, Any?>().apply {
                this[EncodeHintType.MARGIN] = 0
            }

            val bitmapMatrix = try {
                qrCodeWriter.encode(
                    content,
                    BarcodeFormat.QR_CODE,
                    sizePx,
                    sizePx,
                    encodeHints,
                )
            } catch (ex: WriterException) {
                null
            }

            val matrixWidth = bitmapMatrix?.width ?: sizePx
            val matrixHeight = bitmapMatrix?.height ?: sizePx

            val newBitmap =
                createBitmap(bitmapMatrix?.width ?: sizePx, bitmapMatrix?.height ?: sizePx)

            val pixels = IntArray(matrixWidth * matrixHeight)

            for (x in 0 until matrixWidth) {
                for (y in 0 until matrixHeight) {
                    val shouldColorPixel = bitmapMatrix?.get(x, y) ?: false
                    val pixelColor = if (shouldColorPixel) android.graphics.Color.BLACK else android.graphics.Color.WHITE

                    pixels[y * matrixWidth + x] = pixelColor
                }
            }

            newBitmap.setPixels(pixels, 0, matrixWidth, 0, 0, matrixWidth, matrixHeight)

            bitmap = newBitmap
        }
    }

    return bitmap
}


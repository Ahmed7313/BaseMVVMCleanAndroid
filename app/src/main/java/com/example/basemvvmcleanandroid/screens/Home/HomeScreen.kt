package com.example.basemvvmcleanandroid.screens.Home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aramex.mypos.Common.showErrorMsg
import com.daman.edman.screens.components.AppSpacer
import com.daman.edman.screens.components.AppSpacerHeight
import com.daman.edman.screens.components.HeaderText
import com.daman.edman.screens.components.NormalText
import com.example.basemvvmcleanandroid.R
import com.example.basemvvmcleanandroid.common.Constants
import com.example.basemvvmcleanandroid.screens.components.ScanItem
import com.example.basemvvmcleanandroid.ui.theme.OrangeColor
import com.example.basemvvmcleanandroid.ui.theme.SkyColorBlue
import com.example.basemvvmcleanandroid.ui.theme.lightGreenColor
import com.trend.camelx.ui.theme.large
import com.trend.camelx.ui.theme.spacing
import timber.log.Timber

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()

) {


    val context = LocalContext.current
    var sequence by remember { mutableStateOf(0) }
    var handle by remember { mutableStateOf<Int?>(null) }
    var scanning by remember { mutableStateOf(false) }
    var decoded by remember { mutableStateOf(false) } // whether DECODING_DATA arrived


    // Scanner BroadcastReceiver handles OPEN, TRIGGER, DECODING & CLOSE callbacks
    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                val seq = intent.getIntExtra(Constants.EXTRA_INT_DATA3, -1)
                if (seq != sequence) return

                when (intent.action) {
                    // OPEN or CLOSE success
                    Constants.ACTION_BARCODE_CALLBACK_REQUEST_SUCCESS -> {
                        val h = intent.getIntExtra(Constants.EXTRA_HANDLE, 0)
                        if (handle == null) {
                            handle = h
                            // Trigger scan beam
                            context.sendBroadcast(
                                Intent(Constants.ACTION_BARCODE_SET_TRIGGER).apply {
                                    putExtra(Constants.EXTRA_HANDLE, handle)
                                    putExtra(Constants.EXTRA_INT_DATA2, 1)
                                    putExtra(Constants.EXTRA_INT_DATA3, sequence)
                                }
                            )
                        } else {
                            // Close complete
                            scanning = false
                            handle = null
                            decoded = false
                        }
                    }
                    // Barcode decoded
                    Constants.ACTION_BARCODE_CALLBACK_DECODING_DATA -> {
                        decoded = true
                        val data = intent.getByteArrayExtra(Constants.EXTRA_BARCODE_DECODING_DATA)
                        val code = data?.toString(Charsets.UTF_8).orEmpty()
                        viewModel.redeemTicket(code)
                        Timber.tag("codeData").d("Decoded QR Code: $code")
                        Timber.tag("codeData").d("Decoded QR Code: $data")
                        // Close scanner
                        context.sendBroadcast(
                            Intent(Constants.ACTION_BARCODE_CLOSE).apply {
                                putExtra(Constants.EXTRA_HANDLE, handle)
                                putExtra(Constants.EXTRA_INT_DATA3, sequence)
                            }
                        )
                    }
                    // Scan failed or timed out
                    Constants.ACTION_BARCODE_CALLBACK_REQUEST_FAILED -> {
                        if (!decoded) {
                            val err = intent.getIntExtra(Constants.EXTRA_INT_DATA2, -1)
                            context.showErrorMsg(
                                "Scan failed with error code: $err"
                            )
                            Timber.tag("codeData").d("Scan failed with error code: $err")
                        }
                        // Close scanner
                        context.sendBroadcast(
                            Intent(Constants.ACTION_BARCODE_CLOSE).apply {
                                putExtra(Constants.EXTRA_HANDLE, handle)
                                putExtra(Constants.EXTRA_INT_DATA3, sequence)
                            }
                        )
                    }
                }
            }
        }

        val filter = IntentFilter().apply {
            addAction(Constants.ACTION_BARCODE_CALLBACK_REQUEST_SUCCESS)
            addAction(Constants.ACTION_BARCODE_CALLBACK_DECODING_DATA)
            addAction(Constants.ACTION_BARCODE_CALLBACK_REQUEST_FAILED)
        }
        context.registerReceiver(receiver, filter)
        onDispose { context.unregisterReceiver(receiver) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.home_bg_sports),
            contentDescription = "Launcher Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.2f,
            alignment = Alignment.Center,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppSpacer(height = spacing)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    modifier = Modifier
                        .height(100.dp)
                        .weight(3f)
                        .background(color = Color.Black)
                        .border(color = Color.White, width = 1.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Scan Icon",
                        modifier = Modifier
                            .size(50.dp),
                        tint = Color.White
                    )

                    AppSpacer(height = spacing)

                    HeaderText(
                        text = "تطبيق التحقيق",
                        modifier = Modifier,
                        fontSize = 25,
                        color = Color.White
                    )
                }

                ScanItem(
                    modifier = Modifier.weight(1f),
                    showText = false,
                    height = 100,
                    iconSize = 50,
                    icon = R.drawable.left_arrow,
                ) { }


            }

            AppSpacerHeight()

            ScanItem(
                color = SkyColorBlue,
                icon = R.drawable.invalid_card,
                text = "بطاقة"
            ) { }

            AppSpacerHeight()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                ScanItem(
                    modifier = Modifier.weight(1f),
                    color = lightGreenColor,
                    icon = R.drawable.invalid_card,
                    text = "تذكرة"
                ) {
                    if (!scanning) {
                        sequence += 1
                        scanning = true
                        decoded = false
                        handle = null
                        // Open scanner
                        context.sendBroadcast(
                            Intent(Constants.ACTION_BARCODE_OPEN).apply {
                                putExtra(Constants.EXTRA_INT_DATA3, sequence)
                            }
                        )
                    }
                }

                AppSpacer(width = large)

                ScanItem(
                    modifier = Modifier.weight(1f),
                    color = lightGreenColor,
                    icon = R.drawable.invalid_card,
                    text = "تذكرة بالبطاقة"
                ) { }

            }

            AppSpacerHeight()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                ScanItem(
                    modifier = Modifier.weight(1f),
                    color = OrangeColor,
                    icon = R.drawable.invalid_card,
                    text = "تذكرة موسمية"
                ) { }

                AppSpacer(width = large)

                ScanItem(
                    modifier = Modifier.weight(1f),
                    color = OrangeColor,
                    icon = R.drawable.invalid_card,
                    text = "تذكرة موسمية بالبطاقة"
                ) { }


            }

            AppSpacerHeight()

            ScanItem(
                color = SkyColorBlue,
                icon = R.drawable.invalid_card,
                text = "بطاقة"
            ) { }
        }
    }
}
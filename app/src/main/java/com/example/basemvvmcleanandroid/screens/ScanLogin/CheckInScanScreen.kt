
package com.example.basemvvmcleanandroid.screens.ScanLogin

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import apps.max.cash.screens.home.home.qrcode.ScanQrCode
import com.example.basemvvmcleanandroid.screens.ScanLogin.ScanLoginViewModel
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aramex.mypos.Presentation.Components.CustomDialog
import com.example.basemvvmcleanandroid.R
import com.example.basemvvmcleanandroid.common.Constants
import com.example.basemvvmcleanandroid.screens.NavGrapgh.SettingsScreen
import com.example.basemvvmcleanandroid.ui.theme.GreenColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.trend.thecontent.screens.components.LoadingView
import com.trend.thecontent.screens.components.MainButton
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.nio.charset.Charset


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanLoginScreen(
    navController: NavController,
    viewModel: ScanLoginViewModel = hiltViewModel()
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val context = LocalContext.current
    var scannedValue by remember { mutableStateOf<String?>(null) }
    var showCamera by remember { mutableStateOf(false) }
    var isBeamActive by remember { mutableStateOf(false) }
    var userWantsBeamScanner by remember { mutableStateOf(false) } // NEW: Track user intention

    // Track scanner state
    var sequence by remember { mutableStateOf(0) }
    var handle by remember { mutableStateOf<Int?>(null) }

    // Process scanned QR code value
    fun processScannedValue(value: String) {
        try {
            val jsonObject = JSONObject(value)
            val url = jsonObject.getString("url")
            val token = jsonObject.getString("token")

            // Save data and trigger login
            viewModel.saveQRData(url, token)

            // Make sure to call the API with the full URL
            viewModel.scanLogin(token)

            Timber.tag("ScanLogin").d("Processing QR - URL: $url, Token: $token")
        } catch (e: JSONException) {
            Timber.tag("ScanLogin").e(e, "Invalid QR JSON format: $value")
            // Show error to user
            Toast.makeText(context, "Invalid QR code format", Toast.LENGTH_SHORT).show()
        }
    }

    // Handle QR code from either scanner
    LaunchedEffect(scannedValue) {
        scannedValue?.let { value ->
            if (value.isNotEmpty()) {
                processScannedValue(value)
                scannedValue = null // Clear after processing
            }
        }
    }

    // Navigation handling
    LaunchedEffect(key1 = viewModel.navigate) {
        viewModel.navigate.collect {
            if (it) {
                navController.navigate(SettingsScreen)
            }
        }
    }

    // Function to start the scanner beam
    fun startBeamScanner() {
        if (!isBeamActive) {
            isBeamActive = true
            sequence++ // Start new scan sequence

            // Open scanner
            context.sendBroadcast(
                Intent(Constants.ACTION_BARCODE_OPEN).apply {
                    putExtra(Constants.EXTRA_INT_DATA3, sequence)
                }
            )
            Timber.tag("ScanLogin").d("Starting beam scanner with sequence: $sequence")
        }
    }

    // Function to stop the scanner beam
    fun stopBeamScanner() {
        if (isBeamActive) {
            isBeamActive = false
            context.sendBroadcast(
                Intent(Constants.ACTION_BARCODE_CLOSE).apply {
                    putExtra(Constants.EXTRA_HANDLE, handle)
                    putExtra(Constants.EXTRA_INT_DATA3, sequence)
                }
            )
            Timber.tag("ScanLogin").d("Stopping beam scanner")
        }
    }

    // Scanner BroadcastReceiver
    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                val seq = intent.getIntExtra(Constants.EXTRA_INT_DATA3, -1)
                if (seq != sequence) return

                when (intent.action) {
                    Constants.ACTION_BARCODE_CALLBACK_REQUEST_SUCCESS -> {
                        val h = intent.getIntExtra(Constants.EXTRA_HANDLE, 0)
                        if (handle == null) {
                            handle = h
                            Timber.tag("ScanLogin").d("Beam scanner opened successfully, handle: $h")
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
                            handle = null
                            Timber.tag("ScanLogin").d("Beam scanner closed")
                        }
                    }
                    Constants.ACTION_BARCODE_CALLBACK_DECODING_DATA -> {
                        val data = intent.getByteArrayExtra(Constants.EXTRA_BARCODE_DECODING_DATA)
                        val code = data?.toString(Charset.forName("UTF-8")).orEmpty()

                        Timber.tag("ScanLogin").d("Beam scanner decoded QR Code: $code")

                        if (code.isNotEmpty()) {
                            scannedValue = code
                            // Stop scanner after successful scan
                            stopBeamScanner()
                            val jsonObject = JSONObject(code)
                            val url = jsonObject.getString("url")
                            val token = jsonObject.getString("token")

                            // Save data and trigger login
                            viewModel.saveQRData(url, token)

                            // Make sure to call the API with the full URL
                            viewModel.scanLogin(token)
                        }
                    }
                    Constants.ACTION_BARCODE_CALLBACK_REQUEST_FAILED -> {
                        val err = intent.getIntExtra(Constants.EXTRA_INT_DATA2, -1)
                        Timber.tag("ScanLogin").e("Failed to scan QR code: $err")
                        stopBeamScanner()
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
        onDispose {
            context.unregisterReceiver(receiver)
            if (isBeamActive) {
                stopBeamScanner()
            }
        }
    }

    // REMOVED: Automatic beam scanner start on permission grant
    // Now only starts when user explicitly chooses beam scanner

    // Handle scanner mode switching
    LaunchedEffect(showCamera, userWantsBeamScanner) {
        if (cameraPermissionState.status.isGranted) {
            if (showCamera && isBeamActive) {
                // Stop beam scanner when switching to camera
                stopBeamScanner()
            } else if (!showCamera && userWantsBeamScanner && !isBeamActive) {
                // Start beam scanner when user chooses it
                startBeamScanner()
            }
        }
    }

    // Background image
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(R.drawable.home_bg_sports),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.2f
        )

        // Show loading indicator
        LoadingView(viewModel.isLoadingProgressBar.collectAsState(initial = false).value)

        // Main content
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // UI based on permission state
            when {
                cameraPermissionState.status.isGranted -> {
                    if (showCamera) {
                        // Camera scanning view
                        CustomDialog(
                            showDialog = true,
                            onDismissRequest = {
                                showCamera = false
                                userWantsBeamScanner = false
                            }
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                // Camera view
                                ScanQrCode(
                                    modifier = Modifier.fillMaxSize(),
                                    onQrCodeDetected = { value ->
                                        Timber.tag("ScanLogin").d("Camera detected QR Code: $value")
                                        scannedValue = value
                                        showCamera = false // Close camera after scan
                                    }
                                )

                                // Button to go back to beam scanner
                                MainButton(
                                    onClick = {
                                        showCamera = false
                                        userWantsBeamScanner = true
                                    },
                                    text = "Use Scanner Beam",
                                    color = GreenColor,
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(16.dp)
                                )
                            }
                        }
                    } else {
                        // Initial choice screen or beam scanner view
                        if (userWantsBeamScanner) {
                            // Beam scanner view
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = if (isBeamActive) "Scanner beam is active" else "Starting scanner...",
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(16.dp)
                                )

                                if (isBeamActive) {
                                    Text(
                                        "Point the device at a QR code",
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }

                                // Button to switch to camera
                                MainButton(
                                    onClick = {
                                        showCamera = true
                                        userWantsBeamScanner = false
                                    },
                                    text = "Scan with Camera",
                                    color = GreenColor,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .size(width = 200.dp, height = 50.dp)
                                )
                            }
                        } else {
                            // Initial choice screen
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Choose scanning method:",
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(16.dp)
                                )

                                // Button to use beam scanner
                                MainButton(
                                    onClick = {
                                        userWantsBeamScanner = true
                                    },
                                    text = "Use Scanner Beam",
                                    color = GreenColor,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(width = 200.dp, height = 50.dp)
                                )

                                // Button to use camera
                                MainButton(
                                    onClick = {
                                        showCamera = true
                                    },
                                    text = "Scan with Camera",
                                    color = GreenColor,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(width = 200.dp, height = 50.dp)
                                )
                            }
                        }
                    }
                }
                cameraPermissionState.status.shouldShowRationale -> {
                    PermissionDeniedMessage(
                        permissionState = cameraPermissionState,
                        message = "Camera permission is required to scan QR codes. Please grant permission in app settings."
                    )
                }
                else -> {
                    PermissionDeniedMessage(
                        permissionState = cameraPermissionState,
                        message = "Camera permission denied. Tap to request again."
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionDeniedMessage(
    permissionState: PermissionState,
    message: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, modifier = Modifier.padding(16.dp))
        Button(onClick = { permissionState.launchPermissionRequest() }) {
            Text("Request Permission")
        }
    }
}
package com.example.basemvvmcleanandroid.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aramex.mypos.Presentation.Components.MainEditText
import com.daman.edman.screens.components.AppSpacer
import com.example.basemvvmcleanandroid.R
import com.example.basemvvmcleanandroid.presentation.viewmodel.LoginViewModel
import com.example.basemvvmcleanandroid.screens.NavGrapgh.LoginScreen
import com.example.basemvvmcleanandroid.screens.NavGrapgh.SettingsScreen
import com.trend.camelx.ui.theme.spacing
import com.trend.thecontent.screens.components.LoadingView
import com.trend.thecontent.screens.components.MainButton
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    // Subscribe to Compose State<ResponseState>
    val state by viewModel.state

    // Listen for navigation events
    LaunchedEffect(Unit) {
        viewModel.navigateHome.collectLatest {

        }
    }

    LaunchedEffect(key1 = viewModel.navigate) {
        viewModel.navigate.collect {
            if (it) {
                navController.navigate(SettingsScreen)
            }
        }
    }

    LoadingView(viewModel.isLoadingProgressBar.collectAsState(initial = false).value)


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    MainEditText(
                        text = email,
                        onTextChange = { email = it },
                        label = "البريد الإلكتروني",
                        keyboardType = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        isError = false,
                    )

                    var passwordVisible by remember { mutableStateOf(false) }

                    MainEditText(
                        text = email,
                        onTextChange = { email = it },
                        label = "كلمة المرور",
                        keyboardType = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        isError = false,
                        trailingIcon = {
                            val image =
                                if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                            val description =
                                if (passwordVisible) "Hide password" else "Show password"
                            Icon(
                                imageVector = image,
                                contentDescription = description,
                                modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                            )
                        }
                    )

                    if (state.isError.isNotBlank()) {
                        Text(
                            text = state.isError,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    AppSpacer(height = spacing)

                    MainButton(
                        text = "تسجيل الدخول",
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        viewModel.login(
                            LoginViewModel.LoginModel(
                                user = email.trim(),
                                password = password.trim()
                            )
                        )
                    }
                }

            }
        }

package com.example.basemvvmcleanandroid.presentation.viewmodel

import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aramex.mypos.Data.remote.DataWrapper.Resource
import com.aramex.mypos.Data.remote.DataWrapper.ResponseState
import com.example.basemvvmcleanandroid.model.useCases.Auth.LoginUseCase
import com.trend.thecontent.data.local.preference.SavePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.basemvvmcleanandroid.BuildConfig
import com.example.basemvvmcleanandroid.model.useCases.room.SaveLoginResponseUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferences: SavePreferences,
    private val saveLoginResponseUseCase: SaveLoginResponseUseCase
) : ViewModel() {

    private val TAG = "LoginViewModel"

    private val _state = mutableStateOf(ResponseState())
    val state: State<ResponseState> = _state
    val msg = mutableStateOf("")

    private val _stateFingerPrint = mutableStateOf(ResponseState())
    val stateFingerPrint: State<ResponseState> = _stateFingerPrint

    val _navigateHome = MutableSharedFlow<Boolean>()
    val navigateHome = _navigateHome.asSharedFlow()

    val _navigate = MutableSharedFlow<Boolean>()
    val navigate = _navigate.asSharedFlow()

    private val _isLoadingProgressBar = MutableStateFlow<Boolean>(false)
    val isLoadingProgressBar = _isLoadingProgressBar.asStateFlow()

    fun login(model: LoginModel) {
        loginUseCase(createRequestBody(model)).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    Timber.tag(TAG).d("login: loading")
                    _isLoadingProgressBar.value = true
                    _state.value = ResponseState(isLoading = true)
                }

                is Resource.Success -> {
                    _isLoadingProgressBar.value = false
                    _state.value = ResponseState(isLoading = false)

                    Timber.tag(TAG).i("Login successful: ${response.data?.api_token}")

                    response.data?.let { loginData ->
                        // Check if login was successful
                        if (loginData.api_token.isNotEmpty()) {
                            // Save to preferences
                            preferences.putToken(loginData.api_token)
                            preferences.putOrganizer(loginData.organizer)

                            // Handle event_allowed_types for isSport preference
                            when (loginData.event_allowed_types) {
                                "sports" -> preferences.putIsSport(true)
                                "entertainment" -> preferences.putIsSport(false)
                                "all" -> preferences.putIsSport(null)
                            }

                            // Save complete LoginDTO to Room database
                            viewModelScope.launch {
                                try {
                                    saveLoginResponseUseCase(loginData)
                                    Timber.tag(TAG).d("Login response saved to database")
                                } catch (e: Exception) {
                                    Timber.tag(TAG).e(e, "Error saving login response to database")
                                }
                            }

                            // Navigate to next screen
                            _navigate.emit(true)
                        } else {
                            // Handle empty token case
                            _state.value = ResponseState(
                                isLoading = false,
                                isError = "Invalid credentials or empty token received"
                            )
                            msg.value = "Login failed: Invalid credentials"
                        }
                    } ?: run {
                        // Handle null response data
                        _state.value = ResponseState(
                            isLoading = false,
                            isError = "No response data received"
                        )
                        msg.value = "Login failed: No response data"
                    }
                }

                is Resource.Error -> {
                    Timber.tag(TAG).e("login: error - ${response.message}")
                    _isLoadingProgressBar.value = false
                    _state.value = ResponseState(
                        isLoading = false,
                        isError = response.message ?: "Login failed"
                    )
                    msg.value = response.message ?: "Login failed"
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun createRequestBody(model: LoginModel): RequestBody {
        val json = JSONObject().apply {
            put("hardware_brand", Build.BRAND)
            put("hardware_model", Build.MODEL)
            put("os_name", "Android")
            put("os_version", Build.VERSION.RELEASE)
            put("software_brand", "MyApp")
            put("software_version", BuildConfig.VERSION_NAME)
            put("user", model.user)
            put("password", model.password)
        }
        return json.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())
    }

    // Clear any error states
    fun clearError() {
        _state.value = ResponseState()
        msg.value = ""
    }

    // Reset loading state
    fun resetLoadingState() {
        _isLoadingProgressBar.value = false
        _state.value = ResponseState()
    }

    data class LoginModel(
        val user: String,
        val password: String
    )
}
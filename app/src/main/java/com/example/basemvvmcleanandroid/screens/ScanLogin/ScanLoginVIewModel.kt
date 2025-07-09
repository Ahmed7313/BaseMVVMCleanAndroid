package com.example.basemvvmcleanandroid.screens.ScanLogin

import android.os.Build
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aramex.mypos.Data.Preference.Preferences
import com.aramex.mypos.Data.remote.DataWrapper.Resource
import com.aramex.mypos.Data.remote.DataWrapper.ResponseState
import com.example.basemvvmcleanandroid.BuildConfig
import com.example.basemvvmcleanandroid.data.remote.DTO.Login.LoginDTO
import com.example.basemvvmcleanandroid.model.useCases.Auth.LoginScanUseCase
import com.example.basemvvmcleanandroid.model.useCases.room.SaveLoginResponseUseCase
import com.google.mlkit.vision.barcode.common.Barcode
import com.trend.thecontent.data.local.preference.SavePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class ScanLoginViewModel @Inject constructor(
    private val preferences: SavePreferences,
    private val loginScanUseCase: LoginScanUseCase,
    private val saveLoginResponseUseCase: SaveLoginResponseUseCase
): ViewModel() {

    private val TAG = "LoginViewModel"


    private val _state = mutableStateOf(ResponseState())
    val state: State<ResponseState> = _state
    val msg = mutableStateOf("")

    private val _stateFingerPrint = mutableStateOf(ResponseState())
    val stateFingerPrint: State<ResponseState> = _stateFingerPrint


    val _navigateHome = MutableSharedFlow<Boolean>()
    val navigateHome = _navigateHome.asSharedFlow()


    var token = mutableStateOf("")

    val _navigate = MutableSharedFlow<Boolean>()
    val navigate = _navigate.asSharedFlow()

    private val _isLoadingProgressBar = MutableSharedFlow<Boolean>()
    val isLoadingProgressBar = _isLoadingProgressBar.asSharedFlow()


    private val _isUrlUpdated = MutableStateFlow(false)
    val isUrlUpdated = _isUrlUpdated.asStateFlow()

    fun saveQRData(url: String, token: String) {
        preferences.putBaseUrl(url)
        preferences.putToken(token)
        _isUrlUpdated.value = true
    }

    fun scanLogin(token: String) {
        loginScanUseCase(createRequestBody(token)).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    Timber.tag(TAG).d("getIntro: loading")
                    _isLoadingProgressBar.emit(true)
                }

                is Resource.Success -> {
                    _isLoadingProgressBar.emit(false)
                    Timber.tag(TAG).i(response.data?.api_token)

                    if (response.data?.api_token?.isEmpty() != true) {
                        preferences.putToken(response.data?.api_token ?: "")
                        preferences.putOrganizer(response.data?.organizer ?: "")

                        response.data?.let { loginData ->
                            when (loginData.event_allowed_types) {
                                "sports" -> preferences.putIsSport(true)
                                "entertainment" -> preferences.putIsSport(false)
                                "all" -> preferences.putIsSport(null)
                            }

                            // Save entire LoginDTO to Room database
                            viewModelScope.launch {
                                saveLoginResponseUseCase(loginData)
                            }
                        }

                        _navigate.emit(true)
                    }
                }

                is Resource.Error -> {
                    Timber.tag(TAG).d("getIntro: error")
                    _isLoadingProgressBar.emit(false)
                    Timber.tag(TAG).i(response.message)
                    Timber.tag(TAG).i("Error message set: ${msg.value}")
                }
            }
        }.launchIn(viewModelScope)
    }




    private fun createRequestBody(token: String): RequestBody {
        val json = JSONObject().apply {
            put("token", token)
            put("hardware_brand", Build.BRAND)
            put("hardware_model", Build.MODEL)
            put("os_name", "Android")
            put("os_version", Build.VERSION.RELEASE)
            put("software_version", BuildConfig.VERSION_NAME)
            put("software_brand", "BlueBird Android")
        }
        return json.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())
    }

}
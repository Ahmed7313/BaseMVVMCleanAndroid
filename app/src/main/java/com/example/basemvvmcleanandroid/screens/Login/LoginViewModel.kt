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
    private val useCase: LoginUseCase,
    private val preferences: SavePreferences,
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

    private val _isLoadingProgressBar = MutableSharedFlow<Boolean>()
    val isLoadingProgressBar = _isLoadingProgressBar.asSharedFlow()


    fun login(model: LoginModel) {
        useCase(createRequestBody(model)).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    Timber.tag(TAG).d("getIntro: loading")
                    _isLoadingProgressBar.emit(true)
                }

                is Resource.Success -> {

                    _isLoadingProgressBar.emit(false)

                    Timber.tag(TAG).i(response.data?.api_token)


                    if (response.data?.api_token?.isEmpty() != true)
                        _navigate.emit(true)
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

    private fun createRequestBody(model : LoginModel): RequestBody {
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

    data class LoginModel(
        val user: String,
        val password: String
    )
}

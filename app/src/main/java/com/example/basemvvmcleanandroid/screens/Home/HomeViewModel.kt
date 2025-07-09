
package com.example.basemvvmcleanandroid.screens.Home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aramex.mypos.Data.remote.DataWrapper.Resource
import com.aramex.mypos.Data.remote.DataWrapper.ResponseState
import com.example.basemvvmcleanandroid.model.useCases.home.RedeemTicketUseCase
import com.trend.thecontent.data.local.preference.SavePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val redeemTicketUseCase: RedeemTicketUseCase,
    private val preferences: SavePreferences
) : ViewModel() {

    private val TAG = "HomeViewModel"

    // Redemption state
    private val _redemptionState = mutableStateOf(ResponseState())
    val redemptionState: State<ResponseState> = _redemptionState

    private val _isLoadingProgressBar = MutableStateFlow<Boolean>(false)
    val isLoadingProgressBar = _isLoadingProgressBar.asStateFlow()

    // Success message for redemption
    private val _redemptionSuccess = MutableSharedFlow<String>()
    val redemptionSuccess = _redemptionSuccess.asSharedFlow()

    // Scanner state
    private val _shouldStartScanner = MutableStateFlow(false)
    val shouldStartScanner = _shouldStartScanner.asStateFlow()

    fun startTicketScanner() {
        Timber.tag(TAG).d("Starting ticket scanner")
        _shouldStartScanner.value = true
    }

    fun stopTicketScanner() {
        Timber.tag(TAG).d("Stopping ticket scanner")
        _shouldStartScanner.value = false
    }

    fun redeemTicket(positionSecret: String) {
        if (positionSecret.isEmpty()) {
            _redemptionState.value = ResponseState(
                isLoading = false,
                isError = "Invalid ticket code"
            )
            return
        }

        // Validate required data
        val eventSlug = preferences.getSelectedEventSlug()
        val checkInListId = preferences.getCheckInListId()
        val organizer = preferences.getOrganizer()

        if (eventSlug.isEmpty() || checkInListId == -1 || organizer.isEmpty()) {
            _redemptionState.value = ResponseState(
                isLoading = false,
                isError = "Missing event or check-in list information"
            )
            return
        }

        Timber.tag(TAG).d("Redeeming ticket with position secret: $positionSecret")
        Timber.tag(TAG).d("Event slug: $eventSlug, Check-in list ID: $checkInListId")

        redeemTicketUseCase(positionSecret).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    Timber.tag(TAG).d("redeemTicket: loading")
                    _isLoadingProgressBar.value = true
                    _redemptionState.value = ResponseState(isLoading = true)
                }

                is Resource.Success -> {
                    _isLoadingProgressBar.value = false
                    _redemptionState.value = ResponseState(isLoading = false)

                    Timber.tag(TAG).i("Ticket redeemed successfully: ${response.data?.status}")

                    response.data?.let { redemptionData ->
                        when (redemptionData.status.lowercase()) {
                            "ok" -> {
                                val attendeeName = redemptionData.position?.attendee_name ?: "Unknown"
                                val eventName = redemptionData.event?.name ?: "Unknown Event"
                                val successMessage = "Ticket redeemed successfully!\nAttendee: $attendeeName\nEvent: $eventName"

                                _redemptionSuccess.emit(successMessage)
                                Timber.tag(TAG).d("Redemption successful for: $attendeeName")
                            }
                            "error" -> {
                                val errorMessage = redemptionData.attention_message ?: "Redemption failed"
                                _redemptionState.value = ResponseState(
                                    isLoading = false,
                                    isError = errorMessage
                                )
                            }
                            else -> {
                                _redemptionState.value = ResponseState(
                                    isLoading = false,
                                    isError = "Unknown redemption status: ${redemptionData.status}"
                                )
                            }
                        }
                    } ?: run {
                        _redemptionState.value = ResponseState(
                            isLoading = false,
                            isError = "No redemption data received"
                        )
                    }
                }

                is Resource.Error -> {
                    Timber.tag(TAG).e("redeemTicket: error - ${response.message}")
                    _isLoadingProgressBar.value = false
                    _redemptionState.value = ResponseState(
                        isLoading = false,
                        isError = response.message ?: "Failed to redeem ticket"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    // Clear error states
    fun clearRedemptionError() {
        _redemptionState.value = ResponseState()
    }

    // Reset loading state
    fun resetRedemptionState() {
        _isLoadingProgressBar.value = false
        _redemptionState.value = ResponseState()
    }
}
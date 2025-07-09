
package com.example.basemvvmcleanandroid.screens.Settings

import android.os.Build
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.aramex.mypos.Common.showErrorMsg
import com.aramex.mypos.Data.remote.DataWrapper.Resource
import com.aramex.mypos.Data.remote.DataWrapper.ResponseState
import com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO.EventDTO
import com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO.Result
import com.example.basemvvmcleanandroid.model.useCases.Settings.GetEventsParams
import com.example.basemvvmcleanandroid.model.useCases.Settings.GetEventsUseCase
import com.example.basemvvmcleanandroid.model.useCases.Settings.GetCheckInListsUseCase
import com.example.basemvvmcleanandroid.model.useCases.Settings.GetCheckInListsParams
import com.trend.thecontent.data.local.preference.SavePreferences
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basemvvmcleanandroid.data.local.preference.GatePreferences
import com.example.basemvvmcleanandroid.data.remote.DTO.Login.Gate
import com.example.basemvvmcleanandroid.model.useCases.room.GetGatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val getCheckInListsUseCase: GetCheckInListsUseCase,
    private val preferences: SavePreferences,
    private val getGatesUseCase: GetGatesUseCase,
    private val gatePreferences: GatePreferences
) : ViewModel() {

    private val TAG = "SettingsViewModel"

    private val _events = mutableStateListOf<Result>()
    val events: SnapshotStateList<Result> = _events

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Gates state
    private val _availableGates = MutableStateFlow<List<Gate>>(emptyList())
    val availableGates: StateFlow<List<Gate>> = _availableGates.asStateFlow()

    private val _selectedGate = mutableStateOf<Gate?>(null)
    val selectedGate: State<Gate?> = _selectedGate

    private val _isGateDropdownExpanded = mutableStateOf(false)
    val isGateDropdownExpanded: State<Boolean> = _isGateDropdownExpanded

    private val _isLoadingGates = mutableStateOf(false)
    val isLoadingGates: State<Boolean> = _isLoadingGates

    // Check-in lists state using Resource pattern
    private val _checkInListState = mutableStateOf(ResponseState())
    val checkInListState: State<ResponseState> = _checkInListState

    private val _isLoadingProgressBar = MutableStateFlow<Boolean>(false)
    val isLoadingProgressBar = _isLoadingProgressBar.asStateFlow()

    // Navigation flow
    private val _navigateToHome = MutableSharedFlow<Boolean>()
    val navigateToHome = _navigateToHome.asSharedFlow()

    init {
        loadGates()
        loadSelectedGate()
        fetchEvents()
    }

    private fun loadGates() {
        _isLoadingGates.value = true

        getGatesUseCase().onEach { gates ->
            _availableGates.value = gates
            _isLoadingGates.value = false

            Timber.tag(TAG).d("Gates loaded: ${gates.size}")

            // If no gate is selected and gates are available, select the first one
            if (_selectedGate.value == null && gates.isNotEmpty()) {
                selectGate(gates.first())
            }
        }.launchIn(viewModelScope)
    }

    private fun loadSelectedGate() {
        _selectedGate.value = gatePreferences.getSelectedGate()
    }

    fun selectGate(gate: Gate) {
        viewModelScope.launch {
            _selectedGate.value = gate
            gatePreferences.saveSelectedGate(gate)
            _isGateDropdownExpanded.value = false

            Timber.tag(TAG).d("Gate selected: ${gate.name}")
        }
    }

    fun toggleGateDropdown() {
        _isGateDropdownExpanded.value = !_isGateDropdownExpanded.value
    }

    fun closeGateDropdown() {
        _isGateDropdownExpanded.value = false
    }

    fun refreshGates() {
        loadGates()
    }

    fun clearSelectedGate() {
        viewModelScope.launch {
            _selectedGate.value = null
            gatePreferences.clearSelectedGate()
        }
    }

    fun fetchEvents() {
        _isLoading.value = true

        val isSport = preferences.getIsSport()

        val params = GetEventsParams(
            dateToAfter = getCurrentDate(),
            dateToBefore = getCurrentDate(),
            live = true,
            isSport = if (isSport != null) isSport else null
        )

        getEventsUseCase(params).onEach { response ->
            when (response) {
                is Resource.Loading -> Timber.d("Fetching events...")
                is Resource.Success -> {
                    response.data?.results?.let { results ->
                        _events.clear()
                        _events.addAll(results)
                    }
                    _error.value = null
                }
                is Resource.Error -> {
                    _error.value = response.message ?: "Unknown error"
                }
            }
            _isLoading.value = false
        }.launchIn(viewModelScope)
    }

    fun proceedToHome(selectedEvent: Result) {
        if (selectedEvent.slug?.isEmpty() ?: false) {
            _checkInListState.value = ResponseState(
                isLoading = false,
                isError = "Invalid event selected"
            )
            return
        }

        Timber.tag(TAG).d("Starting proceedToHome for event: ${selectedEvent.slug}")

        val params = GetCheckInListsParams(eventSlug = selectedEvent.slug?: "")

        getCheckInListsUseCase(params).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    Timber.tag(TAG).d("proceedToHome: loading check-in lists")
                    _isLoadingProgressBar.value = true
                    _checkInListState.value = ResponseState(isLoading = true)
                }

                is Resource.Success -> {
                    _isLoadingProgressBar.value = false
                    _checkInListState.value = ResponseState(isLoading = false)

                    Timber.tag(TAG).i("Check-in lists loaded successfully")

                    response.data?.let { checkInListData ->
                        if (checkInListData.results?.isNotEmpty() == true) {
                            val firstCheckInListId = checkInListData.results.first()?.id

                            // Save the selected event slug and checkin list ID
                            preferences.putSelectedEventSlug(selectedEvent.slug?: "")
                            preferences.putCheckInListId(firstCheckInListId?: 0)

                            Timber.tag(TAG).d("Event selected: ${selectedEvent.slug}")
                            Timber.tag(TAG).d("Check-in list ID saved: $firstCheckInListId")

                            // Emit navigation event
                            _navigateToHome.emit(true)
                        } else {
                            Timber.tag(TAG).e("No check-in lists found for event")
                            _checkInListState.value = ResponseState(
                                isLoading = false,
                                isError = "No check-in lists found for this event"
                            )
                        }
                    } ?: run {
                        Timber.tag(TAG).e("No check-in list data received")
                        _checkInListState.value = ResponseState(
                            isLoading = false,
                            isError = "No check-in list data received"
                        )
                    }
                }

                is Resource.Error -> {
                    Timber.tag(TAG).e("proceedToHome: error - ${response.message}")
                    _isLoadingProgressBar.value = false
                    _checkInListState.value = ResponseState(
                        isLoading = false,
                        isError = response.message ?: "Failed to load check-in lists"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    // Clear error states
    fun clearCheckInListError() {
        _checkInListState.value = ResponseState()
    }

    // Reset loading state
    fun resetCheckInListState() {
        _isLoadingProgressBar.value = false
        _checkInListState.value = ResponseState()
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
            .format(Date())
    }
}
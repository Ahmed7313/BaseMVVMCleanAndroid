package com.example.basemvvmcleanandroid.screens.ScanLogin

import com.example.basemvvmcleanandroid.data.remote.DTO.Login.LoginDTO

sealed interface BarScanState {
    data object Ideal : BarScanState
    data class ScanSuccess(val barStateModel: LoginDTO) : BarScanState
    data class Error(val error: String) : BarScanState
    data object Loading : BarScanState
}
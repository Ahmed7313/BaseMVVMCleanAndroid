package com.example.basemvvmcleanandroid.screens.ScanLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aramex.mypos.Data.Preference.Preferences
import com.example.basemvvmcleanandroid.data.remote.DTO.Login.LoginDTO
import com.google.mlkit.vision.barcode.common.Barcode
import com.trend.thecontent.data.local.preference.SavePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScanLoginVIewModel @Inject constructor(
    val preferences: SavePreferences
): ViewModel(){



}

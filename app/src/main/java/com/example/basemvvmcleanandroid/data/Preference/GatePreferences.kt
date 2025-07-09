package com.example.basemvvmcleanandroid.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.basemvvmcleanandroid.data.remote.DTO.Login.Gate
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

class GatePreferences @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("gate_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val SELECTED_GATE_KEY = "selected_gate"
        private const val SELECTED_GATE_ID_KEY = "selected_gate_id"
    }

    fun saveSelectedGate(gate: Gate) {
        sharedPreferences.edit {
            putString(SELECTED_GATE_KEY, gson.toJson(gate))
                .putString(
                    SELECTED_GATE_ID_KEY,
                    gate.id.toString()
                ) // Assuming Gate has an id field
        }
    }

    fun getSelectedGate(): Gate? {
        val gateJson = sharedPreferences.getString(SELECTED_GATE_KEY, null)
        return if (gateJson != null) {
            gson.fromJson(gateJson, Gate::class.java)
        } else {
            null
        }
    }

    fun getSelectedGateId(): String? {
        return sharedPreferences.getString(SELECTED_GATE_ID_KEY, null)
    }

    fun clearSelectedGate() {
        sharedPreferences.edit()
            .remove(SELECTED_GATE_KEY)
            .remove(SELECTED_GATE_ID_KEY)
            .apply()
    }
}
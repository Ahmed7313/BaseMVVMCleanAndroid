package com.example.basemvvmcleanandroid.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_response")
data class LoginResponseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val apiToken: String,
    val deviceId: Int,
    val eventAllowedTypes: String,
    val gateJson: String, // Store as JSON string since Room doesn't support List<Gate> directly
    val name: String,
    val organizer: String,
    val securityProfile: String,
    val uniqueSerial: String,
    val timestamp: Long = System.currentTimeMillis()
)

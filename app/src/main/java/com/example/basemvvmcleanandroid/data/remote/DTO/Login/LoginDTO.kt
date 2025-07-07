package com.example.basemvvmcleanandroid.data.remote.DTO.Login

data class LoginDTO(
    val api_token: String,
    val device_id: Int,
    val event_allowed_types: String,
    val gate: List<Gate>,
    val name: String,
    val organizer: String,
    val security_profile: String,
    val unique_serial: String
)
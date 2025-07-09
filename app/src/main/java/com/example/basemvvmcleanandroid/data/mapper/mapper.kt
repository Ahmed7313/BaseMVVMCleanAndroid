package com.example.basemvvmcleanandroid.data.mapper


import com.example.basemvvmcleanandroid.data.remote.DTO.Login.Gate
import com.example.basemvvmcleanandroid.data.remote.DTO.Login.LoginDTO
import com.example.basemvvmcleanandroid.data.room.entities.LoginResponseEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private val gson = Gson()

fun LoginDTO.toEntity(): LoginResponseEntity {
    return LoginResponseEntity(
        apiToken = this.api_token,
        deviceId = this.device_id,
        eventAllowedTypes = this.event_allowed_types,
        gateJson = gson.toJson(this.gate), // Convert List<Gate> to JSON string
        name = this.name,
        organizer = this.organizer,
        securityProfile = this.security_profile,
        uniqueSerial = this.unique_serial
    )
}

fun LoginResponseEntity.toDTO(): LoginDTO {
    val gateType = object : TypeToken<List<Gate>>() {}.type
    val gateList = gson.fromJson<List<Gate>>(this.gateJson, gateType)

    return LoginDTO(
        api_token = this.apiToken,
        device_id = this.deviceId,
        event_allowed_types = this.eventAllowedTypes,
        gate = gateList,
        name = this.name,
        organizer = this.organizer,
        security_profile = this.securityProfile,
        unique_serial = this.uniqueSerial
    )
}

// HERE IS THE getGateList() EXTENSION FUNCTION
fun LoginResponseEntity.getGateList(): List<Gate> {
    val gson = Gson()
    val gateType = object : TypeToken<List<Gate>>() {}.type
    return gson.fromJson(this.gateJson, gateType)
}


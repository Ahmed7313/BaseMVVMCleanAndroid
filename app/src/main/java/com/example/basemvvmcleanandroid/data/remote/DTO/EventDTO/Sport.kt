package com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sport(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "is_active")
    val isActive: Boolean?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "type")
    val type: String?
)
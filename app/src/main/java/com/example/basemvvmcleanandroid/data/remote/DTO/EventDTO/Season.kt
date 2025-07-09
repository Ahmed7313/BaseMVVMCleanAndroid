package com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Season(
    @Json(name = "end_date")
    val endDate: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "is_active")
    val isActive: Boolean?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "start_date")
    val startDate: String?
)
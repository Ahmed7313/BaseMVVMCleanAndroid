package com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VenueName(
    @Json(name = "ar")
    val ar: String?,
    @Json(name = "en")
    val en: String?
)
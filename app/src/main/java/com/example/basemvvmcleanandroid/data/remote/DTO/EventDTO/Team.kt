package com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Team(
    @Json(name = "country")
    val country: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "logo")
    val logo: String?,
    @Json(name = "name")
    val name: Name?,
    @Json(name = "short_name")
    val shortName: ShortName?,
    @Json(name = "type")
    val type: String?
)
package com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventDTO(
    @Json(name = "count")
    val count: Int? = null,
    @Json(name = "next")
    val next: String? = null,
    @Json(name = "previous")
    val previous: Any? = null,
    @Json(name = "results")
    val results: List<Result>? = emptyList()
)
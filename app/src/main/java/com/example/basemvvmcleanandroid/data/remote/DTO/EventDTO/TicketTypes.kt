package com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TicketTypes(
    @Json(name = "eticket")
    val eticket: Double?,
    @Json(name = "physical")
    val physical: Double?
)
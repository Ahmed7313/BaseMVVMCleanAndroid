package com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VenueFacilities(
    @Json(name = "Adipisci deserunt ve")
    val adipisciDeseruntVe: Boolean?,
    @Json(name = "cafe")
    val cafe: Boolean?,
    @Json(name = "elevator")
    val elevator: Boolean?,
    @Json(name = "parking")
    val parking: Boolean?,
    @Json(name = "Prayer Rooms")
    val prayerRooms: Boolean?,
    @Json(name = "restaurant")
    val restaurant: Boolean?,
    @Json(name = "Sed dolores quidem v")
    val sedDoloresQuidemV: Boolean?,
    @Json(name = "Unde irure quod ut e")
    val undeIrureQuodUtE: Boolean?,
    @Json(name = "wc")
    val wc: Boolean?,
    @Json(name = "wheelchair")
    val wheelchair: Boolean?
)
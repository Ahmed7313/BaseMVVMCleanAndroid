package com.example.basemvvmcleanandroid.data.remote.DTO.CheckInListDTO


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "addon_match")
    val addonMatch: Boolean?,
    @Json(name = "all_products")
    val allProducts: Boolean?,
    @Json(name = "allow_entry_after_exit")
    val allowEntryAfterExit: Boolean?,
    @Json(name = "allow_multiple_entries")
    val allowMultipleEntries: Boolean?,
    @Json(name = "checkin_count")
    val checkinCount: Int?,
    @Json(name = "consider_tickets_used")
    val considerTicketsUsed: Boolean?,
    @Json(name = "exit_all_at")
    val exitAllAt: Any?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "ignore_in_statistics")
    val ignoreInStatistics: Boolean?,
    @Json(name = "include_pending")
    val includePending: Boolean?,
    @Json(name = "limit_products")
    val limitProducts: List<Any?>?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "position_count")
    val positionCount: Int?,
    @Json(name = "rules")
    val rules: Rules?,
    @Json(name = "subevent")
    val subevent: Any?
)
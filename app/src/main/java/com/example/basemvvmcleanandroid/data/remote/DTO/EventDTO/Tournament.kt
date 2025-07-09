package com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tournament(
    @Json(name = "description")
    val description: Description?,
    @Json(name = "end_date")
    val endDate: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "is_active")
    val isActive: Boolean?,
    @Json(name = "location")
    val location: LocationX?,
    @Json(name = "logo_url")
    val logoUrl: String?,
    @Json(name = "name")
    val name: Name?,
    @Json(name = "preferred_team")
    val preferredTeam: String?,
    @Json(name = "refund_guarantee")
    val refundGuarantee: Boolean?,
    @Json(name = "season")
    val season: Season?,
    @Json(name = "sport")
    val sport: Sport?,
    @Json(name = "start_date")
    val startDate: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "ticket_type")
    val ticketType: String?,
    @Json(name = "ticket_types")
    val ticketTypes: TicketTypes?
)
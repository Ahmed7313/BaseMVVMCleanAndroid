package com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VenueInfo(
    @Json(name = "address_name")
    val addressName: AddressName?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "location")
    val location: Location?,
    @Json(name = "logo")
    val logo: String?,
    @Json(name = "venue_address")
    val venueAddress: String?,
    @Json(name = "venue_coordinates")
    val venueCoordinates: VenueCoordinates?,
    @Json(name = "venue_facilities")
    val venueFacilities: VenueFacilities?,
    @Json(name = "venue_id")
    val venueId: Int?,
    @Json(name = "venue_name")
    val venueName: VenueName?,
    @Json(name = "venue_terms_of_entry")
    val venueTermsOfEntry: String?
)
package com.daman.edman.data.remote.DTO.Login


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class  User(
    @Json(name = "balance")
    val balance: String?,
    @Json(name = "code")
    val code: Int?,
    @Json(name = "email")
    val email: Any?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "image")
    val image: Any?,
    @Json(name = "isProfileCompleted")
    val isProfileCompleted: Boolean?,
    @Json(name = "language")
    val language: String?,
    @Json(name = "name")
    val name: Any?,
    @Json(name = "phone")
    val phone: String?
)
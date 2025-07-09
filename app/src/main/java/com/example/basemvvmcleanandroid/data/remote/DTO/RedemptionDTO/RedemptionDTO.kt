package com.example.basemvvmcleanandroid.data.remote.DTO.Redemption

data class RedemptionDTO(
    val status: String,
    val position: RedemptionPosition?,
    val list: RedemptionList?,
    val event: RedemptionEvent?,
    val checkin: RedemptionCheckin?,
    val require_attention: Boolean?,
    val attention_message: String?
)

data class RedemptionPosition(
    val id: Int,
    val order: String,
    val positionid: Int,
    val item: String,
    val variation: String?,
    val price: String,
    val attendee_name: String?,
    val attendee_email: String?,
    val secret: String,
    val subevent: Int?
)

data class RedemptionList(
    val id: Int,
    val name: String,
    val checkin_text: String?
)

data class RedemptionEvent(
    val id: Int,
    val name: String,
    val slug: String,
    val timezone: String,
    val date_from: String,
    val date_to: String?
)

data class RedemptionCheckin(
    val datetime: String,
    val type: String,
    val gate: String?
)
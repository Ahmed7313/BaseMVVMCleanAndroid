package com.aramex.mypos.Domain.ReposInterfaces

import com.example.basemvvmcleanandroid.data.remote.DTO.CheckInListDTO.CheckInListDTO
import com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO.EventDTO
import com.example.basemvvmcleanandroid.data.remote.DTO.Redemption.RedemptionDTO
import okhttp3.RequestBody


interface IHomeRepository {

    suspend fun getEvents (
        url: String,
        dateToAfter: String,
        dateToBefore: String,
        live: Boolean = true,
        isSport: Boolean? = null
    ): EventDTO

    suspend fun getCheckInLists(url: String): CheckInListDTO

    suspend fun redeemTicket(url: String): RedemptionDTO


}
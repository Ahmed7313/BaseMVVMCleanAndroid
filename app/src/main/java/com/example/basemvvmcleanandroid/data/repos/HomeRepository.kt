package com.aramex.mypos.Data.Repos

import com.aramex.mypos.Data.remote.ApiServices
import com.aramex.mypos.Domain.ReposInterfaces.IHomeRepository
import com.example.basemvvmcleanandroid.data.remote.DTO.CheckInListDTO.CheckInListDTO
import com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO.EventDTO
import com.example.basemvvmcleanandroid.data.remote.DTO.Redemption.RedemptionDTO
import com.trend.thecontent.data.local.preference.SavePreferences
import okhttp3.RequestBody

import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val services: ApiServices,
    private val preferences: SavePreferences
) : IHomeRepository {

    override suspend fun getEvents(
        url : String,
        dateToAfter: String,
        dateToBefore: String,
        live: Boolean,
        isSport: Boolean?
    ) =  services.getEvents(
        url = url,
        dateToAfter = dateToAfter,
        dateToBefore = dateToBefore,
        live = live,
        isSport = isSport
    )


    override suspend fun getCheckInLists(url: String): CheckInListDTO {
        return services.getCheckInLists(url)
    }

    override suspend fun redeemTicket(url: String): RedemptionDTO {
        return services.redeemTicket(url)
    }


}

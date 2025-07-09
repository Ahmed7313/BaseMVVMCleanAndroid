package com.example.basemvvmcleanandroid.model.useCases.home

import com.aramex.mypos.Data.remote.DataWrapper.Resource
import com.aramex.mypos.Domain.ReposInterfaces.IHomeRepository
import com.example.basemvvmcleanandroid.data.remote.DTO.Redemption.RedemptionDTO
import com.trend.thecontent.data.local.preference.SavePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import javax.inject.Inject

class RedeemTicketUseCase @Inject constructor(
    private val repo: IHomeRepository,
    private val preferences: SavePreferences
) {
    operator fun invoke(positionSecret: String): Flow<Resource<RedemptionDTO>> = flow {
        emit(Resource.Loading())
        try {
            val baseUrl = preferences.getBaseUrl().removeSuffix("/")
            val organizer = preferences.getOrganizer()
            val eventSlug = preferences.getSelectedEventSlug()
            val checkInListId = preferences.getCheckInListId()

            val url = "$baseUrl/api/v1/organizers/$organizer/events/$eventSlug/checkinlists/$checkInListId/positions/$positionSecret/redeem/"

            val result = repo.redeemTicket(url)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to redeem ticket"))
        }
    }
}


package com.example.basemvvmcleanandroid.model.useCases.Settings

import com.aramex.mypos.Data.remote.DataWrapper.Resource
import com.aramex.mypos.Domain.ReposInterfaces.IHomeRepository
import com.aramex.mypos.Domain.UseCases.BaseUseCase
import com.example.basemvvmcleanandroid.data.remote.DTO.CheckInListDTO.CheckInListDTO
import com.trend.thecontent.data.local.preference.SavePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCheckInListsUseCase @Inject constructor(
    private val repo: IHomeRepository,
    private val preferences: SavePreferences
) {
    operator fun invoke(params: GetCheckInListsParams): Flow<Resource<CheckInListDTO>> = flow {
        emit(Resource.Loading())
        try {
            val baseUrl = preferences.getBaseUrl().removeSuffix("/")
            val organizer = preferences.getOrganizer()

            val result = repo.getCheckInLists(
                url = "$baseUrl/api/v1/organizers/$organizer/events/${params.eventSlug}/checkinlists"
            )
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load check-in lists"))
        }
    }
}

data class GetCheckInListsParams(
    val eventSlug: String
)

package com.example.basemvvmcleanandroid.model.useCases.Settings

import com.aramex.mypos.Domain.ReposInterfaces.IHomeRepository
import com.aramex.mypos.Domain.UseCases.BaseUseCase
import com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO.EventDTO
import com.trend.thecontent.data.local.preference.SavePreferences
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val repo: IHomeRepository,
    private val preferences: SavePreferences
) : BaseUseCase<GetEventsParams, EventDTO>() {
    override suspend fun execute(params: GetEventsParams?): EventDTO {
        requireNotNull(params) { "Params cannot be null" }

        val baseUrl = preferences.getBaseUrl().removeSuffix("/")
        val organizer = preferences.getOrganizer()

        return repo.getEvents(
            url = "$baseUrl/api/v1/organizers/$organizer/events",
            dateToAfter = params.dateToAfter,
            dateToBefore = params.dateToBefore,
            live = params.live,
            isSport = params.isSport
        )
    }
}


data class GetEventsParams(
    val dateToAfter: String,
    val dateToBefore: String,
    val live: Boolean,
    val isSport: Boolean?
)

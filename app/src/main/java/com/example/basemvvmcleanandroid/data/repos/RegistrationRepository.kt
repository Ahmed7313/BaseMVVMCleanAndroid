package com.aramex.mypos.Data.Repos

import com.aramex.mypos.Data.Preference.Preferences
import com.aramex.mypos.Data.remote.ApiServices
import com.aramex.mypos.Domain.ReposInterfaces.IRegistrationRepository
import com.example.basemvvmcleanandroid.data.remote.DTO.Login.LoginDTO
import com.trend.thecontent.data.local.preference.SavePreferences

import okhttp3.RequestBody
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    private val services: ApiServices,
    private val preferences: SavePreferences
) : IRegistrationRepository {

    override suspend fun login(body: RequestBody) = services.login(body)

    override suspend fun loginScan(body: RequestBody) = services.loginScan(body = body, url = preferences.getBaseUrl() + "/api/v1/device/initialize")

}
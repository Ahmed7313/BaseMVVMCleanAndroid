package com.aramex.mypos.Data.Repos

import com.aramex.mypos.Data.remote.ApiServices
import com.aramex.mypos.Domain.ReposInterfaces.IRegistrationRepository

import okhttp3.RequestBody
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    private val services: ApiServices,
) : IRegistrationRepository {

    override suspend fun login(body: RequestBody) = services.login(body)

}
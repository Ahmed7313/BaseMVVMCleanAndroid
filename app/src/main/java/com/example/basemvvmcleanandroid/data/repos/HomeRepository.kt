package com.aramex.mypos.Data.Repos

import com.aramex.mypos.Data.remote.ApiServices
import com.aramex.mypos.Domain.ReposInterfaces.IHomeRepository

import okhttp3.RequestBody
import javax.inject.Inject

class HomeRepository  @Inject constructor(
    private val services: ApiServices,
) : IHomeRepository {

}

package com.example.basemvvmcleanandroid.model.useCases.Auth

import com.aramex.mypos.Domain.ReposInterfaces.IRegistrationRepository
import com.aramex.mypos.Domain.UseCases.BaseUseCase
import com.example.basemvvmcleanandroid.data.remote.DTO.Login.LoginDTO
import okhttp3.RequestBody
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: IRegistrationRepository
) : BaseUseCase<RequestBody, LoginDTO>() {
    override suspend fun execute(params: RequestBody?): LoginDTO {
        requireNotNull(params) { "RequestBody must not be null" }
        return repository.login(params)
    }
}
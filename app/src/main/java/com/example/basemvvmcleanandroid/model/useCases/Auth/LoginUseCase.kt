package com.example.basemvvmcleanandroid.model.useCases.Auth

import com.aramex.mypos.Domain.ReposInterfaces.IRegistrationRepository
import com.aramex.mypos.Domain.UseCases.BaseUseCase
import com.daman.edman.data.remote.DTO.Login.LoginResponseDTO
import okhttp3.RequestBody
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: IRegistrationRepository
) : BaseUseCase<RequestBody, LoginResponseDTO>() {
    override suspend fun execute(params: RequestBody?): LoginResponseDTO {
        requireNotNull(params) { "RequestBody must not be null" }
        return repository.login(params)
    }
}
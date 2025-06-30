package com.aramex.mypos.Domain.ReposInterfaces


import com.daman.edman.data.remote.DTO.Login.LoginResponseDTO
import okhttp3.RequestBody

interface IRegistrationRepository {

suspend fun login(body: RequestBody) : LoginResponseDTO

}
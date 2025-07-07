package com.aramex.mypos.Domain.ReposInterfaces


import com.example.basemvvmcleanandroid.data.remote.DTO.Login.LoginDTO
import okhttp3.RequestBody

interface IRegistrationRepository {

suspend fun login(body: RequestBody) : LoginDTO

}
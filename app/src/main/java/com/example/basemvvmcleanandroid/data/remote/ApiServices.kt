package com.aramex.mypos.Data.remote



import com.daman.edman.data.remote.DTO.Login.LoginResponseDTO
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {


    @POST("auth/login")
    suspend fun login(
        @Body body: RequestBody
    ): LoginResponseDTO
}
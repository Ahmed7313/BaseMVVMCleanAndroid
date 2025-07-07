package com.aramex.mypos.Data.remote



import com.example.basemvvmcleanandroid.data.remote.DTO.Login.LoginDTO
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {


    @POST("api/v1/device/user-login")
    suspend fun login(
        @Body body: RequestBody
    ): LoginDTO
}
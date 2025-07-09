package com.aramex.mypos.Data.remote



import com.example.basemvvmcleanandroid.data.remote.DTO.CheckInListDTO.CheckInListDTO
import com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO.EventDTO
import com.example.basemvvmcleanandroid.data.remote.DTO.Login.LoginDTO
import com.example.basemvvmcleanandroid.data.remote.DTO.Redemption.RedemptionDTO
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiServices {


    @POST("api/v1/device/user-login")
    suspend fun login(
        @Body body: RequestBody
    ): LoginDTO


    @POST
    suspend fun loginScan(
        @Url url: String,
        @Body body: RequestBody
    ): LoginDTO

    @GET
    suspend fun getEvents(
        @Url url: String,
        @Query("date_to_after") dateToAfter: String,
        @Query("date_to_befor") dateToBefore: String,
        @Query("live") live: Boolean = true,
        @Query("is_sport") isSport: Boolean? = null
    ): EventDTO

    @GET
    suspend fun getCheckInLists(
        @Url url: String
    ): CheckInListDTO

    @POST
    suspend fun redeemTicket(
        @Url url: String
    ): RedemptionDTO



}
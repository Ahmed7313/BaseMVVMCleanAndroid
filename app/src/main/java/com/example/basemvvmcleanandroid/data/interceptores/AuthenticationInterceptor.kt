package com.aramex.mypos.Data.Interceptores

import android.os.Build
import androidx.annotation.RequiresApi
import com.aramex.mypos.Data.Preference.Preferences
import com.aramex.mypos.Data.remote.APIConstants.ACCEPT
import com.aramex.mypos.Data.remote.APIConstants.AUTH_ENDPOINT
import com.aramex.mypos.Data.remote.ApiParameters.AUTH_HEADER
import com.aramex.mypos.Data.remote.ApiParameters.TOKEN_TYPE
import com.squareup.moshi.Moshi
import com.trend.thecontent.data.local.preference.SavePreferences
import okhttp3.*
import java.io.IOException
import java.time.Instant
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor( val preferences: SavePreferences) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val token: String = preferences.getToken() // Retrieve token from DataStore
        val language: String = preferences.getLanguage() // Retrieve language from DataStore
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Device $token")
            .header("Accept-Language", language)
            .header("Accept", "application/json")
            .build()
        return chain.proceed(newRequest)
    }
}


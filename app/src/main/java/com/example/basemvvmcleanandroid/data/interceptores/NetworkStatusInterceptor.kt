package com.aramex.mypos.Data.NetworkChecker

import android.Manifest
import androidx.annotation.RequiresPermission
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkStatusInterceptor @Inject constructor(private val connectionManager: ConnectionManager) : Interceptor {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (connectionManager.isConnected()) {
            chain.proceed(chain.request())
        } else {
            throw NetworkUnavailableException()
        }
    }
}

class NoMoreAnimalsException(message: String): Exception(message)

class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)
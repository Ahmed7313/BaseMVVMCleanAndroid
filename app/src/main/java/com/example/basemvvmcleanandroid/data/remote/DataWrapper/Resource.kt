package com.aramex.mypos.Data.remote.DataWrapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

}

enum class ListState {
    IDLE,
    LOADING,
    PAGINATING,
    ERROR,
    PAGINATION_EXHAUST,
}

data class ResponseState (
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: String = "",
)


class OffsetDateTimeAdapter {
    @RequiresApi(Build.VERSION_CODES.O)
    @ToJson
    fun toJson(value: OffsetDateTime): String {
        return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @FromJson
    fun fromJson(value: String): OffsetDateTime {
        return OffsetDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

}
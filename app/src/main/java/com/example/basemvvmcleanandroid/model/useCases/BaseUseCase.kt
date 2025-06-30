package com.aramex.mypos.Domain.UseCases

import com.aramex.mypos.Common.showErrorMsg
import com.aramex.mypos.Data.remote.DataWrapper.Resource
import com.example.basemvvmcleanandroid.BaseApp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection


abstract class BaseUseCase<in P, T>(private val defaultParams: P? = null) {

    private val TAG = "BaseUseCase"

    operator fun invoke(params: P? = defaultParams): Flow<Resource<T>> = flow {
        emit(Resource.Loading<T>())
        try {
            val result = execute(params)
            emit(Resource.Success(result))
            Timber.tag(TAG).d("invoke: Success with result $result")
        } catch (e: HttpException) {
            val errorMessage = handleHttpException(e)
            emit(Resource.Error<T>(errorMessage))
        } catch (e: IOException) {
            val errorMessage = "Network error"
            showError(errorMessage)
            emit(Resource.Error<T>(errorMessage))
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Unexpected error occurred")
            val errorMessage = "An unexpected error occurred"
            showError(errorMessage)
            emit(Resource.Error<T>(errorMessage))
        }
    }

    protected abstract suspend fun execute(params: P?): T

    private fun handleHttpException(exception: HttpException): String {
        val errorMessage = parseErrorResponse(exception) ?: "Unknown error"
        when (exception.code()) {
            HttpURLConnection.HTTP_NOT_FOUND -> {
                val message = "Resource not found: $errorMessage"
                showError(message)
                return message
            }
            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                val message = "Server error: $errorMessage"
                showError(message)
                return message
            }
            else -> {
                val message = "HTTP error ${exception.code()}: $errorMessage"
                showError(message)
                return message
            }
        }
    }

    private fun parseErrorResponse(exception: HttpException): String? {
        return try {
            val response = exception.response()?.errorBody()?.string()
            Timber.tag(TAG).d("Raw error response: $response")

            val contentType = exception.response()?.headers()?.get("Content-Type")
            if (contentType?.contains("application/json") == true) {
                val jsonObject = response?.let { JSONObject(it) }
                val errorMessage = jsonObject?.optString("error")
                    ?: jsonObject?.optString("message")
                    ?: jsonObject?.optString("description")
                Timber.tag(TAG).d("Parsed error message: $errorMessage")
                errorMessage
            } else {
                Timber.tag(TAG).d("Non-JSON error response received")
                response
            }
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Failed to parse error response")
            "Error retrieving response"
        }
    }

    private fun showError(message: String) {
        BaseApp.appContext.showErrorMsg(message)
        Timber.tag(TAG).d("Error: $message")
    }
}






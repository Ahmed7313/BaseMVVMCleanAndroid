package com.example.basemvvmcleanandroid.data.repository

import com.example.basemvvmcleanandroid.data.local.dao.LoginResponseDao
import com.example.basemvvmcleanandroid.data.room.entities.LoginResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginResponseRepository @Inject constructor(
    private val loginResponseDao: LoginResponseDao
) {

    fun getAllLoginResponses(): Flow<List<LoginResponseEntity>> {
        return loginResponseDao.getAllLoginResponses()
    }

    fun getLatestLoginResponse(): Flow<LoginResponseEntity?> {
        return loginResponseDao.getLatestLoginResponse()
    }

    suspend fun getLoginResponseById(id: Int): LoginResponseEntity? {
        return loginResponseDao.getLoginResponseById(id)
    }

    suspend fun getLoginResponseByToken(apiToken: String): LoginResponseEntity? {
        return loginResponseDao.getLoginResponseByToken(apiToken)
    }

    suspend fun getLoginResponseByDeviceId(deviceId: Int): LoginResponseEntity? {
        return loginResponseDao.getLoginResponseByDeviceId(deviceId)
    }

    suspend fun insertLoginResponse(loginResponse: LoginResponseEntity) {
        loginResponseDao.insertLoginResponse(loginResponse)
    }

    suspend fun updateLoginResponse(loginResponse: LoginResponseEntity) {
        loginResponseDao.updateLoginResponse(loginResponse)
    }

    suspend fun deleteLoginResponse(loginResponse: LoginResponseEntity) {
        loginResponseDao.deleteLoginResponse(loginResponse)
    }

    suspend fun deleteAllLoginResponses() {
        loginResponseDao.deleteAllLoginResponses()
    }

    suspend fun getCount(): Int {
        return loginResponseDao.getCount()
    }
}

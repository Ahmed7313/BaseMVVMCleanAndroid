package com.example.basemvvmcleanandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.basemvvmcleanandroid.data.room.entities.LoginResponseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoginResponseDao {

    @Query("SELECT * FROM login_response ORDER BY timestamp DESC")
    fun getAllLoginResponses(): Flow<List<LoginResponseEntity>>

    @Query("SELECT * FROM login_response ORDER BY timestamp DESC LIMIT 1")
    fun getLatestLoginResponse(): Flow<LoginResponseEntity?>

    @Query("SELECT * FROM login_response WHERE id = :id")
    suspend fun getLoginResponseById(id: Int): LoginResponseEntity?

    @Query("SELECT * FROM login_response WHERE apiToken = :apiToken")
    suspend fun getLoginResponseByToken(apiToken: String): LoginResponseEntity?

    @Query("SELECT * FROM login_response WHERE deviceId = :deviceId")
    suspend fun getLoginResponseByDeviceId(deviceId: Int): LoginResponseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoginResponse(loginResponse: LoginResponseEntity)

    @Update
    suspend fun updateLoginResponse(loginResponse: LoginResponseEntity)

    @Delete
    suspend fun deleteLoginResponse(loginResponse: LoginResponseEntity)

    @Query("DELETE FROM login_response")
    suspend fun deleteAllLoginResponses()

    @Query("SELECT COUNT(*) FROM login_response")
    suspend fun getCount(): Int
}

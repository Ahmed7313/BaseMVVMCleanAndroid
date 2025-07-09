package com.example.basemvvmcleanandroid.model.useCases.room

import com.example.basemvvmcleanandroid.data.mapper.getGateList
import com.example.basemvvmcleanandroid.data.remote.DTO.Login.Gate
import com.example.basemvvmcleanandroid.data.repository.LoginResponseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGatesUseCase @Inject constructor(
    private val repository: LoginResponseRepository
) {
    operator fun invoke(): Flow<List<Gate>> {
        return repository.getLatestLoginResponse().map { loginResponse ->
            loginResponse?.getGateList() ?: emptyList()
        }
    }
}

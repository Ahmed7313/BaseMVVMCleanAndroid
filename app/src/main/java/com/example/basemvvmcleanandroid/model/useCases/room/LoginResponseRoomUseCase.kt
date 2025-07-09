package com.example.basemvvmcleanandroid.model.useCases.room


import com.example.basemvvmcleanandroid.data.mapper.toEntity
import com.example.basemvvmcleanandroid.data.remote.DTO.Login.LoginDTO
import com.example.basemvvmcleanandroid.data.repository.LoginResponseRepository
import com.example.basemvvmcleanandroid.data.room.entities.LoginResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveLoginResponseUseCase @Inject constructor(
    private val repository: LoginResponseRepository
) {
    suspend operator fun invoke(loginDTO: LoginDTO) {
        repository.insertLoginResponse(loginDTO.toEntity())
    }
}

class GetLatestLoginResponseUseCase @Inject constructor(
    private val repository: LoginResponseRepository
) {
    operator fun invoke(): Flow<LoginResponseEntity?> {
        return repository.getLatestLoginResponse()
    }
}

class GetAllLoginResponsesUseCase @Inject constructor(
    private val repository: LoginResponseRepository
) {
    operator fun invoke(): Flow<List<LoginResponseEntity>> {
        return repository.getAllLoginResponses()
    }
}

class GetLoginResponseByTokenUseCase @Inject constructor(
    private val repository: LoginResponseRepository
) {
    suspend operator fun invoke(apiToken: String): LoginResponseEntity? {
        return repository.getLoginResponseByToken(apiToken)
    }
}

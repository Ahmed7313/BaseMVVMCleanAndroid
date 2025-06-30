package com.aramex.mypos.Data.DI

import com.aramex.mypos.Data.Repos.HomeRepository
import com.aramex.mypos.Data.Repos.RegistrationRepository
import com.aramex.mypos.Data.remote.ApiServices
import com.aramex.mypos.Domain.ReposInterfaces.IHomeRepository
import com.aramex.mypos.Domain.ReposInterfaces.IRegistrationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object  ReposModule {

    @Provides
    fun provideRegistrationRepository (api: ApiServices) : IRegistrationRepository {
        return RegistrationRepository(api)
    }

    @Provides
    fun provideHomeRepository (api: ApiServices) : IHomeRepository {
        return HomeRepository(api)
    }
}
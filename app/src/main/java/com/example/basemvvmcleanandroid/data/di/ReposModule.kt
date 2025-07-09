package com.aramex.mypos.Data.DI

import com.aramex.mypos.Data.Repos.HomeRepository
import com.aramex.mypos.Data.Repos.RegistrationRepository
import com.aramex.mypos.Data.remote.ApiServices
import com.aramex.mypos.Domain.ReposInterfaces.IHomeRepository
import com.aramex.mypos.Domain.ReposInterfaces.IRegistrationRepository
import com.trend.thecontent.data.local.preference.SavePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object  ReposModule {

    @Provides
    fun provideRegistrationRepository (api: ApiServices, preferences: SavePreferences) : IRegistrationRepository {
        return RegistrationRepository(api,preferences)
    }

    @Provides
    fun provideHomeRepository (api: ApiServices , preferences: SavePreferences) : IHomeRepository {
        return HomeRepository(api, preferences)
    }
}
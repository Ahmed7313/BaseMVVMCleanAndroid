package com.aramex.mypos.Data.DI

import com.aramex.mypos.Data.Preference.Preferences
import com.trend.thecontent.data.local.preference.SavePreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

  @Binds
  abstract fun providePreferences(preferences: SavePreferences): Preferences
}
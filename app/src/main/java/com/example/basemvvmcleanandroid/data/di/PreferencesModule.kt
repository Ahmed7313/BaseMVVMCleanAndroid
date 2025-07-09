package com.aramex.mypos.Data.DI

import android.content.Context
import com.aramex.mypos.Data.Preference.Preferences
import com.example.basemvvmcleanandroid.data.local.preference.GatePreferences
import com.google.gson.Gson
import com.trend.thecontent.data.local.preference.SavePreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

  @Provides
  @Singleton
  fun providePreferences(savePreferences: SavePreferences): Preferences {
    return savePreferences
  }

  @Provides
  @Singleton
  fun provideGatePreferences(
    @ApplicationContext context: Context,
    gson: Gson
  ): GatePreferences {
    return GatePreferences(context, gson)
  }

}
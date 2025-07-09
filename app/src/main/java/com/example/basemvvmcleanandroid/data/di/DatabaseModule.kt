package com.example.basemvvmcleanandroid.di

import android.content.Context
import androidx.room.Room
import com.example.basemvvmcleanandroid.data.local.dao.LoginResponseDao
import com.example.basemvvmcleanandroid.data.local.preference.GatePreferences
import com.example.basemvvmcleanandroid.data.repository.LoginResponseRepository

import com.example.basemvvmcleanandroid.data.room.AppDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideLoginResponseDao(database: AppDatabase): LoginResponseDao {
        return database.loginResponseDao()
    }

    @Provides
    @Singleton
    fun provideLoginResponseRepository(loginResponseDao: LoginResponseDao): LoginResponseRepository {
        return LoginResponseRepository(loginResponseDao)
    }

}

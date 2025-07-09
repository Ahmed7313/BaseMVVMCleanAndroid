package com.example.basemvvmcleanandroid.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.basemvvmcleanandroid.data.local.dao.LoginResponseDao
import com.example.basemvvmcleanandroid.data.room.entities.LoginResponseEntity

@Database(
    entities = [LoginResponseEntity::class],
    version = 2, // Updated version
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun loginResponseDao(): LoginResponseDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}
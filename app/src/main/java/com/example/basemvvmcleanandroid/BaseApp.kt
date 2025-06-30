package com.example.basemvvmcleanandroid

import android.app.Application
import android.content.Context
import com.aramex.mypos.Data.Interceptores.Logger
import com.trend.thecontent.data.local.preference.SavePreferences
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApp : Application()  {

    @Inject
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        instance = this
        initLogger()

        val preferences = SavePreferences(applicationContext)

    }

    private fun initLogger() {
        Logger.init()
    }
    companion object {
        lateinit var instance: BaseApp
        lateinit  var appContext: Context
    }
}

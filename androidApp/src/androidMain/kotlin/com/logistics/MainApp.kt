package com.logistics

import android.app.Application

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContext.appContext = applicationContext
    }
}

package ru.mironov.logistics

import android.app.Application
import ru.mironov.common.AppContext

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContext.appContext = applicationContext
    }
}

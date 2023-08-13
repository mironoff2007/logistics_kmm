package ru.mironov.logistics

import android.app.Application
import com.mironov.di.ApplicationComponent
import ru.mironov.common.AppContext

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContext.appContext = applicationContext

        val ktor = ApplicationComponent.getKtor()
        if (isTest()) ktor.setTestMode()
    }

    @Synchronized
    fun isTest(): Boolean {
        val isTest: Boolean = try {
            val runnerName = CustomTestRunner::class.qualifiedName
            Class.forName(runnerName)
            true
        } catch (e: ClassNotFoundException) {
            false
        }
        return isTest
    }
}

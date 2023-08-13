package ru.mironov.logistics

import android.app.Application
import ru.mironov.common.AppContext
import ru.mironov.common.ktor.Ktor

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContext.appContext = applicationContext

        Ktor.isTest = isTest()
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

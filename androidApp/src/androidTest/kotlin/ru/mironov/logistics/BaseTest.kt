package ru.mironov.logistics

import com.mironov.di.ApplicationComponent
import ru.mironov.domain.settings.CommonSettings
import ru.mironov.domain.settings.UserData

abstract class BaseTest {


    open fun before() {
        clearMemory()
    }

    protected fun clearMemory() {
        val dbComponent = ApplicationComponent.getDbComponent()
        val prefs = dbComponent.sharedPrefs
        prefs.clear(UserData())
        prefs.clear(CommonSettings())

        dbComponent.cityDbSource.clear()
        dbComponent.parcelsDbSource.clear()
    }

    open fun after() {
        clearMemory()
    }

}
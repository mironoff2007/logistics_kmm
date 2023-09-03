package ru.mironov.logistics

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.rule.GrantPermissionRule
import com.mironov.di.ApplicationComponent
import org.junit.Rule
import ru.mironov.domain.settings.CommonSettings
import ru.mironov.domain.settings.UserData

abstract class BaseUITest {

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    @get:Rule
    val composeTestRule = createComposeRule()

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
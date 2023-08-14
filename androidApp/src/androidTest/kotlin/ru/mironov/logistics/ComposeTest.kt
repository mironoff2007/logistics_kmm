package ru.mironov.logistics

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mironov.di.ApplicationComponent
import com.mironov.localization.StringRes
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.mironov.common.ktor.auth.AuthResponse
import ru.mironov.common.navigation.TopBar
import ru.mironov.common.res.localizedString
import ru.mironov.domain.model.auth.TokenResp

@RunWith(AndroidJUnit4::class)
class ComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginUiTest() {
        val expireAt = Clock.System.now().toEpochMilliseconds() + 1000 * 360
        val token = TokenResp(token = "", expireAt = expireAt)
        val resp = Json.encodeToString(AuthResponse(token))

        val ktor = ApplicationComponent.getKtor()
        ktor.addNextResponse(resp)

        composeTestRule.setContent {
            MainScreen()
        }

        composeTestRule.waitUntil(10_000) {
            composeTestRule
                .onAllNodesWithTag(ru.mironov.logistics.ui.screens.login.LOGIN_BTN_TAG)
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule
            .onNodeWithTag(ru.mironov.logistics.ui.screens.login.LOGIN_BTN_TAG)
            .performClick()

        composeTestRule
            .onNodeWithTag(TopBar.TITLE_TAG)
            .assert(hasText(localizedString(StringRes.RegisterParcel)))
    }

}
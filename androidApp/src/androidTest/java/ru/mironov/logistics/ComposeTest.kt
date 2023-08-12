package ru.mironov.logistics

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class ComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginUiTest() {
        composeTestRule.setContent {
            MainScreen()
        }

        composeTestRule.waitUntil(10_000) {
            composeTestRule
                .onAllNodesWithTag(ru.mironov.logistics.ui.screens.login.LOGIN_BTN_TAG)
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithTag(ru.mironov.logistics.ui.screens.login.LOGIN_BTN_TAG).performClick()
        composeTestRule.waitUntil(10_000) { true }

        assertEquals(true, true)
    }

}
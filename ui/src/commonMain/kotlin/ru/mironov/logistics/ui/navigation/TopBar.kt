package ru.mironov.common.navigation

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import ru.mironov.common.navigation.TopBar.TITLE_TAG
import ru.mironov.logistics.ui.screens.login.LOGIN_BTN_TAG

object TopBar {
    const val TITLE_TAG = "title"
}
    @Composable
    fun TopBar(title: String = "", buttonIcon: ImageVector, onButtonClicked: () -> Unit) {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.testTag(TITLE_TAG),
                    text = title
                )
            },
            navigationIcon = {
                IconButton(onClick = { onButtonClicked() }) {
                    Icon(buttonIcon, contentDescription = "")
                }
            }
        )
    }




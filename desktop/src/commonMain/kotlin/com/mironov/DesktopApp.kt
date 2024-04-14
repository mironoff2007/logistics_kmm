package com.mironov

import NavRoot
import androidx.compose.runtime.Composable
import com.mironov.di.ApplicationComponent
import ru.mironov.common.ui.theme.LogisticsTheme
import ru.mironov.logistics.ui.navigation.Navigator


@Composable
fun DesktopAppCommon() {
    LogisticsTheme {
        val navigator = Navigator()
        NavRoot(navigator, ApplicationComponent.getVmFactory()) {}
    }
}
package com.mironov

import NavRoot
import androidx.compose.runtime.Composable
import com.mironov.di.ApplicationComponent
import ru.mironov.common.ui.theme.LogisticsTheme


@Composable
fun DesktopAppCommon() {
    LogisticsTheme {
        NavRoot(ApplicationComponent.getVmFactory()) {}
    }
}
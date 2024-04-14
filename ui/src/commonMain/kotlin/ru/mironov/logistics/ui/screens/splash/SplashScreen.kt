package ru.mironov.logistics.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import ru.mironov.common.res.ImageRes
import ru.mironov.common.ui.getLocale
import ui.getPainterResource
import ru.mironov.logistics.ui.navigation.Navigator
import ru.mironov.logistics.ui.navigation.Screens

@Composable
fun SplashScreen(
    navigator: Navigator,
    viewModel: SplashViewModel,
) {
    val goToNextScreen = viewModel.goToNextScreen.collectAsState(false)

    val locale = getLocale()
    LaunchedEffect(Unit) {
        viewModel.onStartUp(locale)
    }

    if (goToNextScreen.value) navigator.navigate(Screens.LoginScreen.getName())
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(300.dp),
            painter = getPainterResource(ImageRes.Box),
            contentDescription = "logo",
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary)
        )
    }
}
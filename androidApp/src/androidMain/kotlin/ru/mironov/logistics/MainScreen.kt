package ru.mironov.logistics

import NavRoot
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mironov.di.ApplicationComponent
import com.mironov.localization.StringRes
import ru.mironov.common.res.localizedString
import ru.mironov.common.ui.theme.LogisticsTheme
import ru.mironov.logistics.ui.theme.Main
import ru.mironov.logistics.ui.theme.MainDark

@Composable
fun MainScreen() {
    val darkTheme = isSystemInDarkTheme()
    val useDarkIcons = false
    val barColor = if (darkTheme) MainDark else Main

    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = barColor,
            darkIcons = useDarkIcons
        )
        onDispose {}
    }
    LogisticsTheme(darkTheme = darkTheme) {
        var onBackPressed by remember { mutableStateOf<(() -> Unit)?>(null) }
        val backPressed = fun(callBack: () -> Unit) {
            onBackPressed = callBack
        }
        BackHandler {
            onBackPressed?.invoke()
            onBackPressed = null
        }

        var grantedWrite by remember { mutableStateOf(false) }
        val grantWriteFlagSwitch = fun() {
            grantedWrite = true
        }
        requestWrite(grantWriteFlagSwitch)

        var grantedRead by remember { mutableStateOf(false) }
        val grantReadFlagSwitch = fun() {
            grantedRead = true
        }
        if (grantedWrite) requestRead(grantReadFlagSwitch)

        if (grantedRead && grantedWrite) {
            NavRoot(ApplicationComponent.getVmFactory(),  backPressed)
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = AnnotatedString(localizedString(StringRes.RequestPermissions)),
                    style = TextStyle(
                        fontFamily = MaterialTheme.typography.h4.fontFamily,
                        fontSize = 25.sp,
                        color = MaterialTheme.colors.onSurface
                    ),
                )
            }
        }
    }
}
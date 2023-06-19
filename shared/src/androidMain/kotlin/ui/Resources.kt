package ru.mironov.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import ru.mironov.common.res.ImageRes

@Composable
actual fun getPainterResource(imageRes: ImageRes): Painter {
    val id = when (imageRes) {
        /*ImageRes.Box -> R.drawable.box
        ImageRes.RegisterParcel -> R.drawable.register_parcel
        ImageRes.Warehouse -> R.drawable.warehouse
        ImageRes.Settings -> R.drawable.settings
        ImageRes.Logout -> R.drawable.logout
        ImageRes.Back -> R.drawable.back_arrow
        ImageRes.Search -> R.drawable.search
        ImageRes.Filter -> R.drawable.filter
        ImageRes.Eye -> R.drawable.eye*/
        else -> 0//R.drawable.none
    }
    return painterResource(id)
}

@Composable
actual fun getLocale(): String {
    val context = LocalContext.current
    return context.resources.configuration.locales.get(0).language
}
package ru.mironov.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import ru.mironov.common.res.ImageRes
import java.util.Locale

@Composable
actual fun getPainterResource(imageRes: ImageRes): Painter {
    val id = when(imageRes) {
        ImageRes.Box -> "box.xml"
        ImageRes.RegisterParcel -> "register_parcel.xml"
        ImageRes.Warehouse -> "warehouse.xml"
        ImageRes.Settings -> "settings.xml"
        ImageRes.Logout -> "logout.xml"
        ImageRes.Back -> "back_arrow.xml"
        ImageRes.Search -> "search.xml"
        ImageRes.Filter -> "filter.xml"
        ImageRes.Eye -> "eye.xml"
        else -> "none.xml"
    }
    return painterResource("drawable/$id")
}

@Composable
actual fun getLocale(): String {
    return Locale.getDefault().language
}
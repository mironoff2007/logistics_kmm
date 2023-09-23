package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ru.mironov.common.res.ImageRes

@OptIn(ExperimentalResourceApi::class)
@Composable
fun getPainterResource(imageRes: ImageRes): Painter
{
    val id = when(imageRes) {
        ImageRes.Compose -> "compose-multiplatform.xml"
        ImageRes.Box -> "box.xml"
        ImageRes.RegisterParcel -> "register_parcel.xml"
        ImageRes.Warehouse -> "warehouse.xml"
        ImageRes.GlobalSearch -> "search_parcel.xml"
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
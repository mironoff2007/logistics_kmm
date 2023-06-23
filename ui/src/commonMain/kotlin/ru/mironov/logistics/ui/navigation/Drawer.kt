package ru.mironov.logistics.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mironov.common.res.localizedString
import ui.getPainterResource

@Composable
fun Drawer(
    onDestinationClicked: (route: String) -> Unit,
    allowedDestinations: List<Screens>,
    drawerWidth: Float
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 25.dp)
            .width(drawerWidth.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(
            items = allowedDestinations,
            key = { item -> item.getName() }
        ) { cell ->
            Destination(onDestinationClicked, cell,)
        }
    }
}

@Composable
fun Destination(
    onDestinationClicked: (route: String) -> Unit,
    screen: Screens,
) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Image(
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                       onDestinationClicked(screen.getName())
                    },
                painter = getPainterResource(screen.icon),
                contentDescription = "menu icon",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface)
            )

            Text(
                modifier = Modifier
                    .weight (0.8f)
                    .padding(start = 15.dp, end = 15.dp)
                    .clickable {
                        onDestinationClicked(screen.getName())
                    },
                text = AnnotatedString(localizedString(screen.title)),
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                ),
                maxLines = 2
            )
        }
}


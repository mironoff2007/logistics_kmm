package ru.mironov.logistics.ui.screens.parceldata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mironov.localization.StringRes
import kotlinx.coroutines.Job
import ru.mironov.common.navigation.TopBar
import ru.mironov.common.res.localizedString
import util.DateTimeFormat
import ru.mironov.domain.model.Parcel
import ru.mironov.logistics.ui.navigation.NavViewModel
import ru.mironov.logistics.ui.navigation.Screens

@Composable
fun ParcelDataScreen(
    openDrawer: () -> Job,
    vm: ParcelDataViewModel,
    navModel: NavViewModel,
    backAction: (() -> Unit) -> Unit
) {

    backAction.invoke { navModel.navigateBack() }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val loading = vm.loading.collectAsState()
        val parcel = vm.parcel.collectAsState()

        TopBar(
            title = localizedString(Screens.RegisterDestinationParcel.title),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
        LaunchedEffect(Unit) {
            val args = navModel.getArgs()
            vm.onScreenOpened(args)
        }


        when {
            loading.value -> CircularProgressIndicator()
            parcel.value != null -> ParcelViewItem(parcel.value!!)
        }
    }
}

@Composable
private fun ParcelViewItem(
    parcel: Parcel
) {
    Card(
        modifier = Modifier.wrapContentWidth().wrapContentHeight().padding(top = 15.dp),
        shape = RoundedCornerShape(25.dp),
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Column(
            modifier = Modifier.padding(25.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = localizedString(StringRes.ParcelId) + " : " + AnnotatedString(parcel.parcelId.toString()),
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            Text(
                text = localizedString(StringRes.FirstName) + " : " + AnnotatedString(parcel.customerName),
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            Text(
                text = localizedString(StringRes.SecondName) + " : " + AnnotatedString(parcel.customerSecondName),
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            Text(
                text = localizedString(StringRes.Address) + " : " + AnnotatedString(parcel.address),
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            Text(
                text = localizedString(StringRes.SenderName) + " : " + AnnotatedString(parcel.senderName),
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            Text(
                text = localizedString(StringRes.SenderSecondName) + " : " + AnnotatedString(parcel.senderSecondName),
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            Text(
                text = localizedString(StringRes.SenderAddress) + " : " + AnnotatedString(parcel.senderAddress),
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            val senderCity = AnnotatedString(parcel.senderCity.name)
            Text(
                text = localizedString(StringRes.SenderCity) + " : " + senderCity,
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            val destinationCity = AnnotatedString(parcel.destinationCity.name)
            Text(
                text = localizedString(StringRes.DestinationCity) + " : " + destinationCity,
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
            val dateTime = AnnotatedString(DateTimeFormat.formatUI(parcel.dateMillis) ?: parcel.dateShow)
            Text(
                text = localizedString(StringRes.ParcelRegTime) + " : " + dateTime,
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                ),
            )
        }
    }
}
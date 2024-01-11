package ru.mironov.logistics.ui.screens.registerresult

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
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
import ru.mironov.domain.model.Parcel
import ru.mironov.logistics.ui.navigation.NavViewModel
import ru.mironov.logistics.ui.navigation.Screens
import util.DateTimeFormat

@Composable
fun RegisterResult(
    openDrawer: () -> Job,
    backAction: (() -> Unit) -> Unit,
    vm: RegisterResultViewModel,
    navModel: NavViewModel
) {

    backAction.invoke { navModel.navigateBack() }

    var result: Boolean? = null
    vm.result.Observe()
    vm.result.onEvent { result = it }

    val showMsg = fun(msg: String) {
        navModel.showMsg(msg)
    }

    when (result) {
        true -> {
            showMsg.invoke(localizedString(StringRes.ParcelIsAdded))
            navModel.navigate(Screens.RegisterSenderParcel.getName())
        }
        else -> {}
    }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            title = localizedString(Screens.RegisterResult.title),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val loading = vm.loading.collectAsState()
            val parcel = vm.parcel.collectAsState()

            LaunchedEffect(Unit) {
                val arg = navModel.getArgs()
                vm.onScreenOpened(arg ?: "")
            }

            val submit = fun() { vm.submit() }
            parcel.value?.let { ParcelViewItem(parcel = it, submit) }
        }
    }
}

@Composable
private fun ParcelViewItem(
    parcel: Parcel,
    submit: () -> Unit,
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(15.dp),
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
            Button(
                modifier = Modifier.padding(top = 25.dp),
                onClick = {
                    submit.invoke()
                }
            ) {
                Text(text = localizedString(StringRes.AddParcel), color = MaterialTheme.colors.onPrimary)
            }
        }
    }
}
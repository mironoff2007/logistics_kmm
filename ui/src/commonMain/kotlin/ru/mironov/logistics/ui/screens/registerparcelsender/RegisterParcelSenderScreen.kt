package ru.mironov.logistics.ui.screens.registerparcelsender

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mironov.localization.StringRes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import ru.mironov.common.navigation.TopBar
import ru.mironov.common.res.localizedString
import ru.mironov.domain.model.City
import ru.mironov.domain.model.ParcelData
import ru.mironov.common.ui.Spinner
import ru.mironov.logistics.ui.navigation.NavViewModel
import ru.mironov.logistics.ui.navigation.Navigator
import ru.mironov.logistics.ui.navigation.Screens

@Composable
fun RegisterParcelSenderScreen(
    openDrawer: () -> Job,
    vm: RegisterParcelSenderViewModel,
    navigator: Navigator
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val loading = vm.loading.collectAsState()
        vm.navWithArg.Observe()

        val cities = vm.cities.collectAsState()

        LaunchedEffect(Unit) {
            vm.onScreenOpened()
        }

        TopBar(
            title = localizedString(Screens.RegisterDestinationParcel.title),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
        val register = fun(senderParcelData: ParcelData) {
            vm.addParcel(senderParcelData)
        }

        vm.navWithArg.onEvent {
            navigator.navigateWithArgs(Screens.RegisterDestinationParcel.getName(), it)
        }

        val updateValues = fun(parcelData: ParcelData) {
            vm.senderName = parcelData.personName
            vm.senderSecondName = parcelData.personSecondName
            vm.senderAddress = parcelData.address
            vm.senderCity = parcelData.city
        }

        val initData = ParcelData(
            personName = vm.senderName,
            personSecondName = vm.senderSecondName,
            address = vm.senderAddress,
            city = vm.senderCity
        )

        RegisterParcelSenderLayout(
            initData,
            register,
            loading.value,
            cities.value.toImmutableList(),
            updateValues,
        )
    }
}

@Composable
fun RegisterParcelSenderLayout(
    initData: ParcelData,
    register: (ParcelData) -> Unit,
    loading: Boolean,
    cities: ImmutableList<City>,
    updateValues: (ParcelData) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = localizedString(StringRes.Sender),
            style = TextStyle(
                fontFamily = MaterialTheme.typography.h4.fontFamily,
                fontSize = 25.sp,
                color = MaterialTheme.colors.onSurface
            ),
        )
        val name = remember { mutableStateOf(TextFieldValue(initData.personName)) }
        val customerSecondName = remember { mutableStateOf(TextFieldValue(initData.personSecondName)) }
        val address = remember { mutableStateOf(TextFieldValue(initData.address)) }
        val city = remember { mutableStateOf(initData.city) }
        val resetSpinner = remember { mutableStateOf(false) }

        val update = fun() {
            updateValues.invoke(
                ParcelData(
                    personName = name.value.text,
                    personSecondName = customerSecondName.value.text,
                    address = address.value.text,
                    city = city.value
                )
            )
        }

        TextField(
            label = { Text(text = localizedString(StringRes.FirstName)) },
            value = name.value,
            onValueChange = {
                name.value = it
                update.invoke()
            },
            isError = name.value.text.isBlank()
        )
        TextField(
            label = { Text(text = localizedString(StringRes.SecondName)) },
            value = customerSecondName.value,
            onValueChange = {
                customerSecondName.value = it
                update.invoke()
            },
            isError = customerSecondName.value.text.isBlank()
        )
        TextField(
            label = { Text(text = localizedString(StringRes.SenderAddress)) },
            value = address.value,
            onValueChange = {
                address.value = it
                update.invoke()
            },
            isError = address.value.text.isBlank()
        )
        Spinner(
            modifier = Modifier.width(250.dp).padding(top = 10.dp),
            list = cities.map { it.name }.toImmutableList(),
            onValueChange = { value ->
                val citySelected = cities.first {
                    it.name == value
                }
                city.value = citySelected
                update.invoke()
            },
            selected = city.value?.name ?: localizedString(StringRes.SelectCity),
            textSize = 25.sp,
            resetSelected = resetSpinner.value
        )

        resetSpinner.value = false

        val senderParcelData = ParcelData(
            personName = name.value.text,
            personSecondName = customerSecondName.value.text,
            address = address.value.text,
            city = city.value
        )

        val hint = when {
            senderParcelData.isCorrect() -> ""
            !senderParcelData.isCorrect() -> localizedString(StringRes.EmptyDataHint)
            else -> ""
        }
        if (senderParcelData.isCorrect())
            Button(
                modifier = Modifier.padding(top = 25.dp),
                enabled = !loading,
                onClick = { register.invoke(senderParcelData) }
            ) {
                Text(text = localizedString(StringRes.Next), color = MaterialTheme.colors.onPrimary)
            }
        else Text(
            modifier = Modifier.padding(top = 25.dp),
            text = hint,
            color = MaterialTheme.colors.onSurface
        )
    }
}

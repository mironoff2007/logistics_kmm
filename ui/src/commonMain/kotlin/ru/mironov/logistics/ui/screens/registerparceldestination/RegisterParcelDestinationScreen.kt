package ru.mironov.logistics.ui.screens.registerparceldestination

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
import ru.mironov.common.ui.Spinner
import ru.mironov.domain.model.City
import ru.mironov.domain.model.ParcelData
import ru.mironov.logistics.ui.navigation.NavViewModel
import ru.mironov.logistics.ui.navigation.Navigator
import ru.mironov.logistics.ui.navigation.Screens

@Composable
fun RegisterParcelDestinationScreen(
    openDrawer: () -> Job,
    vm: RegisterParcelDestinationViewModel,
    navigator: Navigator,
    backAction: (() -> Unit) -> Unit
) {
    backAction.invoke { navigator.navigateBack() }

    LaunchedEffect(Unit) {
        val args = navigator.getArgs()
        vm.onScreenOpened(args)
    }

    vm.navWithArg.Observe()
    vm.navWithArg.onEvent {
        navigator.navigateWithArgs(Screens.RegisterResult.getName(), it)
    }

    val goToNextScreenWithArg = fun(recipientData: ParcelData) {
        vm.addParcel(recipientData)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val loading = vm.loading.collectAsState()
        val cities = vm.cities.collectAsState()

        TopBar(
            title = localizedString(Screens.RegisterDestinationParcel.title),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
        RegisterParcelDestinationLayout(goToNextScreenWithArg, loading.value,  cities.value.toImmutableList(), vm.parcelRecipientData)
    }
}

@Composable
fun RegisterParcelDestinationLayout(
    goToNextScreenWithArg: (ParcelData) -> Unit,
    loading: Boolean,
    cities: ImmutableList<City>,
    initData: ParcelData?
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = localizedString(StringRes.Recipient),
            style = TextStyle(
                fontFamily = MaterialTheme.typography.h4.fontFamily,
                fontSize = 25.sp,
                color = MaterialTheme.colors.onSurface
            ),
        )
        val name = remember { mutableStateOf(TextFieldValue(initData?.personName ?: "")) }
        TextField(
            label = { Text(text = localizedString(StringRes.FirstName)) },
            value = name.value,
            onValueChange = { name.value = it },
            isError = name.value.text.isBlank()
        )
        val customerSecondName = remember { mutableStateOf(TextFieldValue(initData?.personSecondName ?: "")) }
        TextField(
            label = { Text(text = localizedString(StringRes.SecondName)) },
            value = customerSecondName.value,
            onValueChange = { customerSecondName.value = it },
            isError = customerSecondName.value.text.isBlank()
        )
        val address = remember { mutableStateOf(TextFieldValue(initData?.address ?: "")) }
        TextField(
            label = { Text(text = localizedString(StringRes.Address)) },
            value = address.value,
            onValueChange = { address.value = it },
            isError = address.value.text.isBlank()
        )

        val city = remember { mutableStateOf(initData?.city) }
        val resetSpinner = remember { mutableStateOf(false) }
        Spinner(
            modifier = Modifier.width(250.dp).padding(top = 10.dp),
            list = cities.map { it.name }.toImmutableList(),
            onValueChange = { value ->
                val citySelected = cities.first {
                    it.name == value
                }
                city.value = citySelected
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
        if (senderParcelData.isCorrect()) Button(
            modifier = Modifier.padding(top = 25.dp),
            enabled = !loading && senderParcelData.isCorrect(),
            onClick = { goToNextScreenWithArg.invoke(senderParcelData) }
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

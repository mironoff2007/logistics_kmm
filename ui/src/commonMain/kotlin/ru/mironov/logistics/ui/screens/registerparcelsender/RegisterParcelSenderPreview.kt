package ru.mironov.logistics.ui.screens.registerparcelsender

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.toImmutableList
import ru.mironov.domain.model.City
import ru.mironov.domain.model.ParcelData
import ru.mironov.common.ui.theme.LogisticsTheme

@Composable
fun RegisterParcelSenderWithData() {
    val cities = listOf(City(1, "City"))
    LogisticsTheme {
        RegisterParcelSenderLayout(
            initData = ParcelData(
                personName = "Name",
                personSecondName = "Second name",
                address = "Street, home 1, room 1, index 111111",
                city = cities.first()
            ),
            register = { },
            loading = false,
            cities = cities.toImmutableList(),
            updateValues = {},
        )
    }
}

@Composable
fun RegisterParcelSenderEmptyPreview() {
    val cities = listOf(City(1, "City"))
    LogisticsTheme {
        RegisterParcelSenderLayout(
            initData = ParcelData(
                personName = "",
                personSecondName = "",
                address = "",
                city = null
            ),
            register = { },
            loading = false,
            cities = cities.toImmutableList(),
            updateValues = {},
        )
    }
}
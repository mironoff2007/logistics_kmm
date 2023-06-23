package ru.mironov.logistics.ui.screens.warehouse

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mironov.localization.StringRes
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import ru.mironov.common.navigation.TopBar
import ru.mironov.common.res.ImageRes
import ru.mironov.common.res.localizedString
import ui.getPainterResource
import ru.mironov.common.util.DateTimeFormat
import ru.mironov.domain.model.City
import ru.mironov.domain.model.Parcel
import ru.mironov.common.ui.Spinner
import ru.mironov.logistics.ui.navigation.NavViewModel
import ru.mironov.logistics.ui.navigation.Screens

@Composable
fun Warehouse(
    openDrawer: () -> Job,
    vm: WarehouseViewModel,
    navModel: NavViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            title = localizedString(Screens.Warehouse.title),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val loading = vm.loading.collectAsState()
            val parcels = vm.parcels.collectAsState()
            val cities = vm.cities.collectAsState()

            vm.navWithArg.Observe()
            vm.navWithArg.onEvent { arg ->
                arg?.let{ navModel.navigateWithArgs(Screens.ParcelData.getName(), it) }
            }

            val click = fun(id: Long) {
                vm.postArgsAndGo(id)
            }

            var currentCity by remember { mutableStateOf<City?>(null) }
            var destinationCity by remember { mutableStateOf<City?>(null) }
            var search by remember { mutableStateOf("") }

            val onCurrentCitySelected = fun(city: City?) {
                currentCity = city
                vm.search(
                    search = search,
                    currentCity = currentCity,
                    destinationCity = destinationCity
                )
            }
            val onDestinationCitySelected = fun(city: City?) {
                destinationCity = city
                vm.search(
                    search = search,
                    currentCity = currentCity,
                    destinationCity = destinationCity
                )
            }

            val onSearchChange = fun(searchValue: String) {
                search = searchValue
                vm.search(
                    search = searchValue,
                    currentCity = currentCity,
                    destinationCity = destinationCity
                )
            }

            LaunchedEffect(Unit) {
                vm.onScreenOpened(
                    search = search,
                    currentCity = currentCity,
                    destinationCity = destinationCity)
            }

            var showFilter by remember { mutableStateOf(true) }

            val filterIsUsed = currentCity != null || destinationCity != null
            val filterIconColor = if (filterIsUsed) Color.Green else MaterialTheme.colors.onSurface
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Search(onSearchChange)
                Image(
                    modifier = Modifier
                        .size(35.dp)
                        .padding(start = 15.dp)
                        .clickable { showFilter = !showFilter },
                    painter = getPainterResource(ImageRes.Filter),
                    contentDescription = "filter",
                    colorFilter = ColorFilter.tint(color = filterIconColor),
                )
            }
            if (showFilter) Filters(
                cities = cities.value.toPersistentList(),
                onCurrentCityChange = onCurrentCitySelected,
                onDestinationCityChange = onDestinationCitySelected,
                selectedCurrentCity = currentCity,
                selectedDestinationCity = destinationCity
            )
            WarehouseLazyColumn(loading.value, parcels.value, click)
        }
    }
}

@Composable
private fun Search(onSearchChange: (String) -> Unit) {
    val search = remember { mutableStateOf(TextFieldValue()) }

    TextField(
        label = { Text(text = localizedString(StringRes.Search)) },
        value = search.value,
        onValueChange = {
            search.value = it
            onSearchChange.invoke(it.text)
        },
        leadingIcon = {
            Icon(getPainterResource(ImageRes.Search), localizedString(StringRes.Search))
        }
    )
}
@Composable
private fun Filters(
    cities: List<City?>,
    onCurrentCityChange: (value: City?) -> Unit,
    onDestinationCityChange: (value: City?) -> Unit,
    selectedCurrentCity: City?,
    selectedDestinationCity: City?,
) {
    Card(
        modifier = Modifier.wrapContentSize().padding(15.dp),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            CityFilter(
                modifier = Modifier.wrapContentSize(),
                name = localizedString(StringRes.CurrentCity).replace(" ", "\n"),
                cities = cities,
                onValueChange = onCurrentCityChange,
                selected = selectedCurrentCity
            )
            CityFilter(
                modifier = Modifier.wrapContentSize(),
                name = localizedString(StringRes.DestinationCity).replace(" ", "\n"),
                cities = cities,
                onValueChange = onDestinationCityChange,
                selected =  selectedDestinationCity
            )
        }
    }

}

@Composable
private fun CityFilter(
    modifier: Modifier,
    name: String,
    cities: List<City?>,
    onValueChange: (value: City?) -> Unit,
    selected: City?
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier.wrapContentHeight(),
            text = AnnotatedString(name),
            style = TextStyle(
                fontFamily = MaterialTheme.typography.h4.fontFamily,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onSurface
            ),
        )
        val city = remember { mutableStateOf(selected) }
        Spinner(
            modifier = Modifier.width(150.dp).padding(5.dp),
            list = cities.map { it?.name ?: localizedString(StringRes.Any) }.toPersistentList(),
            onValueChange = { value ->
                val citySelected = cities.firstOrNull {
                    it?.name == value
                }
                city.value = citySelected
                onValueChange.invoke(citySelected)
            },
            selected = city.value?.name ?: localizedString(StringRes.Any),
            textSize = 20.sp
        )
    }
}

@Composable
private fun WarehouseLazyColumn(loading: Boolean, parcels: List<Parcel>, click: (Long) -> Unit) {
    if (loading) CircularProgressIndicator()
    else LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(
            items = parcels,
            key = { item -> item.parcelId }
        ) { cell ->
            ParcelViewItem(cell, click)
        }
    }
}

@Composable
private fun ParcelViewItem(
    parcel: Parcel,
    click: (Long) -> Unit
) {
    Card(
        modifier = Modifier.wrapContentWidth().wrapContentHeight().clickable {
                    click.invoke(parcel.parcelId)
        },
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
            val currentCity = AnnotatedString(parcel.currentCity.name)
            Text(
                text = localizedString(StringRes.CurrentCity) + " : " + currentCity,
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
            val dateTime = AnnotatedString(DateTimeFormat.formatUI(parcel.date) ?: parcel.dateShow)
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
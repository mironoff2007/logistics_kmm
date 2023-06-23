package ru.mironov.logistics.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import ru.mironov.domain.settings.Setting
import ru.mironov.domain.settings.SettingItem
import ru.mironov.common.navigation.TopBar
import ru.mironov.common.res.localizedString
import ru.mironov.logistics.ui.navigation.Screens

@Composable
fun SettingsScreen(
    openDrawer: () -> Job,
    vm: SettingsViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val updateItem = fun(setting: Setting<out Any>, value: Any) { vm.updateSetting(setting, value) }

        TopBar(
            title = localizedString(Screens.SettingsScreen.title),
            buttonIcon = Icons.Filled.Menu
        ) { openDrawer() }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LaunchedEffect(Unit) {
                vm.getSettings()
                //vm.logger.logD("SettingsScreen", "Settings screen opened")
            }

            val settings = vm.settings.collectAsState()
            SettingsLazyColumn(
                updateItem = updateItem,
                settings = settings.value.toPersistentList(),
                textStyle = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun SettingsLazyColumn(
    updateItem: (Setting<out Any>, Any) -> Unit,
    settings: ImmutableList<SettingItem>?,
    textStyle: TextStyle,
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = settings ?: emptyList(),
            key = { item -> item.setting.name }
        ) { cell ->
            SettingViewItem(cell, updateItem, textStyle)
        }
    }
}
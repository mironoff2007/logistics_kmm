package ru.mironov.logistics.ui.screens.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mironov.domain.settings.CommonSettings
import ru.mironov.domain.settings.Setting
import ru.mironov.domain.settings.SettingItem
import ru.mironov.logistics.SettingsUpdater
import me.tatarka.inject.annotations.Inject
import ru.mironov.logistics.SharedPreferences
import ru.mironov.common.Logger
import ru.mironov.domain.viewmodel.ViewModel

@Inject
class SettingsViewModel(
    private val prefs: SharedPreferences,
    private val logger: Logger,
    private val settingsUpdater: SettingsUpdater
) : ViewModel() {

    private val _settings = MutableStateFlow<List<SettingItem>>(emptyList())
    val settings: StateFlow<List<SettingItem>>
        get() {
            return _settings.asStateFlow()
        }

    fun getSettings() {
        viewModelScope.launch {
            val commonSettings = prefs.load() ?: CommonSettings()
            val settingsMap = commonSettings.loadByList(listOf(CommonSettings.LogsEnabled, CommonSettings.AppLocale))
            val list = mutableListOf<SettingItem>()
            settingsMap.forEach { list.add(SettingItem(it.key, it.value)) }
            _settings.emit(list)
        }
    }

    fun updateSetting(setting: Setting<out Any>, value: Any) {
        viewModelScope.launch {
            val commonSettings = prefs.load() ?: CommonSettings()
            commonSettings.add(setting, value)
            settingsUpdater.applyChanges(setting, value)
            prefs.save(commonSettings)
            getSettings()
        }
    }

}
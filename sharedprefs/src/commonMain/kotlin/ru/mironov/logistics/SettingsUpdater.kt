package ru.mironov.logistics

import me.tatarka.inject.annotations.Inject
import ru.mironov.domain.settings.CommonSettings
import ru.mironov.domain.settings.Setting
import ru.mironov.common.Logger

class SettingsUpdater @Inject constructor(private val logger: Logger) {

    fun applyChanges(setting: Setting<*>, value: Any) {
        when (setting) {
            is CommonSettings.LogsEnabled -> if (value == true) logger.enable() else logger.disable()
            else -> {}
        }
    }
}
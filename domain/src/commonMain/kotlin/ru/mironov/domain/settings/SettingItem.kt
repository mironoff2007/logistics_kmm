package ru.mironov.domain.settings

data class SettingItem(val setting: Setting<out Any>, val value: Any)
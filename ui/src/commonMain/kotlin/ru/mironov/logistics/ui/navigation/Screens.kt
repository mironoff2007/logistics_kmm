package ru.mironov.logistics.ui.navigation

import com.mironov.localization.StringRes
import ru.mironov.common.res.ImageRes

sealed class Screens(val title: StringRes, val icon: ImageRes) {

    object SplashScreen : Screens(StringRes.None, ImageRes.None)
    object LoginScreen : Screens(StringRes.Login, ImageRes.None)
    object LogoutScreen : Screens(StringRes.Logout, ImageRes.Logout)
    object RegisterResult : Screens(StringRes.RegisterParcel, ImageRes.RegisterParcel)
    object RegisterDestinationParcel : Screens(StringRes.RegisterParcel, ImageRes.RegisterParcel)
    object RegisterSenderParcel : Screens(StringRes.RegisterParcel, ImageRes.RegisterParcel)
    object ParcelData : Screens(StringRes.ParcelData, ImageRes.None)
    object Warehouse : Screens(StringRes.Warehouse, ImageRes.Warehouse)
    object GlobalSearch : Screens(StringRes.GlobalSearch, ImageRes.GlobalSearch)
    object SettingsScreen : Screens(StringRes.Settings, ImageRes.Settings)
    object SettingsScreenLoggedOut : Screens(StringRes.Settings, ImageRes.Settings)
    object Back : Screens(StringRes.Back, ImageRes.Back)

    fun getName(): String {
        return this::class.simpleName ?: ""
    }

    companion object {
        fun getName(route: Screens): String {
           return route.getName()
        }
    }
}
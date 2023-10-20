package ru.mironov.logistics.ui.navigation

import com.mironov.localization.StringRes
import ru.mironov.common.res.ImageRes

sealed class Screens(val title: StringRes, val icon: ImageRes) {

    data object SplashScreen : Screens(StringRes.None, ImageRes.None)
    data object LoginScreen : Screens(StringRes.Login, ImageRes.None)
    data object LogoutScreen : Screens(StringRes.Logout, ImageRes.Logout)
    data object RegisterResult : Screens(StringRes.RegisterParcel, ImageRes.RegisterParcel)
    data object RegisterDestinationParcel : Screens(StringRes.RegisterParcel, ImageRes.RegisterParcel)
    data object RegisterSenderParcel : Screens(StringRes.RegisterParcel, ImageRes.RegisterParcel)
    data object ParcelData : Screens(StringRes.ParcelData, ImageRes.None)
    data object CarCargo : Screens(StringRes.Cargo, ImageRes.Cargo)
    data object Warehouse : Screens(StringRes.Warehouse, ImageRes.Warehouse)
    data object BackPack : Screens(StringRes.Backpack, ImageRes.Backpack)
    data object GlobalSearch : Screens(StringRes.GlobalSearch, ImageRes.GlobalSearch)
    data object SettingsScreen : Screens(StringRes.Settings, ImageRes.Settings)
    data object SettingsScreenLoggedOut : Screens(StringRes.Settings, ImageRes.Settings)
    data object Back : Screens(StringRes.Back, ImageRes.Back)

    fun getName(): String {
        return this::class.simpleName ?: ""
    }

    companion object {
        fun getName(route: Screens): String {
           return route.getName()
        }
    }
}
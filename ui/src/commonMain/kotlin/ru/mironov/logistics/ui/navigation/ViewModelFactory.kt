package ru.mironov.logistics.ui.navigation

import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.domain.di.AppScope
import ru.mironov.domain.viewmodel.AbsViewModel
import ru.mironov.logistics.ui.screens.login.LoginViewModel
import ru.mironov.logistics.ui.screens.parceldata.ParcelDataViewModel
import ru.mironov.logistics.ui.screens.registerparceldestination.RegisterParcelDestinationViewModel
import ru.mironov.logistics.ui.screens.registerparcelsender.RegisterParcelSenderViewModel
import ru.mironov.logistics.ui.screens.registerresult.RegisterResultViewModel
import ru.mironov.logistics.ui.screens.settings.SettingsViewModel
import ru.mironov.logistics.ui.screens.splash.SplashViewModel
import ru.mironov.logistics.ui.screens.store.StoreViewModel
import kotlin.reflect.KClass

@AppScope
@Inject
class ViewModelFactory(
    private val logger: Logger,
    private val splashVm: () -> SplashViewModel,
    private val navVm: () -> SideMenuViewModel,
    private val registerParcelDest: () -> RegisterParcelDestinationViewModel,
    private val registerParcelSender: () -> RegisterParcelSenderViewModel,
    private val registerResult: () -> RegisterResultViewModel,
    private val settingsVm: () -> SettingsViewModel,
    private val loginVm: () -> LoginViewModel,
    private val warehouseVm: () -> StoreViewModel,
    private val parcelDataVm: () -> ParcelDataViewModel
) {

    private val map = LinkedHashMap<String, AbsViewModel>()
    private val keep = mutableListOf<String>()

    private fun KClass<out AbsViewModel>.getKey() = this.simpleName

    fun <T : AbsViewModel> provide(
        type: KClass<out AbsViewModel>,
        keepForever: Boolean = false
    ): T {
        val key = type.getKey()
        return if (map.containsKey(key)) {
            logger.logD(LOG_TAG, "get $key from stored")
            map[key]!! as T
        } else {
            logger.logD(LOG_TAG, "create $key")
            val vm = instantiate<T>(type)
            map[key!!] = vm
            if (keepForever) keep.add(key)
            vm
        }
    }

    private fun <T : AbsViewModel> instantiate(type: KClass<out AbsViewModel>): T  {
        return when (type.simpleName) {
            SideMenuViewModel::class.simpleName  -> navVm()
            SplashViewModel::class.simpleName  -> splashVm()
            RegisterParcelDestinationViewModel::class.simpleName  -> registerParcelDest()
            RegisterParcelSenderViewModel::class.simpleName  -> registerParcelSender()
            RegisterResultViewModel::class.simpleName  -> registerResult()
            SettingsViewModel::class.simpleName  -> settingsVm()
            LoginViewModel::class.simpleName  -> loginVm()
            StoreViewModel::class.simpleName  -> warehouseVm()
            ParcelDataViewModel::class.simpleName  -> parcelDataVm()
            else -> throw Exception("no inject method for ${type.simpleName}, add method to app component")
        }  as T
    }

    fun delete(type: KClass<out AbsViewModel>? = null) {
        val key = type?.getKey()
        if (type == null) {
            val lastKey = map.keys.last()
            map.remove(lastKey)
        } else key?.let { removeIfNotKeep(key) }
    }

    fun removeAll(except: KClass<out AbsViewModel>? = null) {
        logger.logD(LOG_TAG, "remove all method")
        val keys = map.keys.toList()
        keys.forEach { key ->
            if (except?.getKey() != key) removeIfNotKeep(key)
        }
    }

    private fun removeIfNotKeep(key: String) {
        if (!keep.contains(key)) {
            map[key]?.onDestroy()
            map.remove(key)
            logger.logD(LOG_TAG, "remove $key wm")
        }
    }

    companion object {
        const val LOG_TAG = "ViewModelFactory"
    }

}


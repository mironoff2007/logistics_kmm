package com.mironov.di

import com.mironov.database.city.CityDbSource
import com.mironov.database.parcel.ParcelsDbSource
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.mironov.common.Logger
import ru.mironov.common.ktor.client.KtorClient
import ru.mironov.common.ktor.KtorProvider
import ru.mironov.domain.di.AppScope
import ru.mironov.common.ktor.source.auth.AuthApi
import ru.mironov.common.ktor.source.CitiesWebSource
import ru.mironov.common.ktor.source.auth.Auth
import ru.mironov.domain.di.NetworkScope
import ru.mironov.logistics.SharedPreferences
import ru.mironov.logistics.logging.LoggerImpl
import ru.mironov.logistics.ui.navigation.ViewModelFactory

@Component
@AppScope
abstract class ApplicationComponent(
    @Component val serverContractComponent: ServerContractComponent,
    @Component val databaseComponent: DatabaseComponent
) {
    abstract val factory: ViewModelFactory

    companion object {
        private var instance: ApplicationComponent? = null

        private fun getInstance() = instance ?: ApplicationComponent::class
            .create(
                ServerContractComponent::class.create(),
                DatabaseComponent::class.create()
            )
            .also { instance = it }
        fun getVmFactory() = getInstance().factory
        fun getKtor() = getInstance().serverContractComponent.ktor
        fun getDbComponent() = getInstance().databaseComponent

    }
    val LoggerImpl.bind: Logger
        @Provides get() = this

    val KtorProvider.bind: KtorClient
        @Provides get() = this

}


@Component
abstract class DatabaseComponent() {
    abstract val sharedPrefs: SharedPreferences
    abstract val parcelsDbSource: ParcelsDbSource
    abstract val cityDbSource: CityDbSource
}

@NetworkScope
@Component
abstract class ServerContractComponent() {

    abstract val ktor: KtorClient

    val Auth.bind: AuthApi
        @Provides get() = this

    val KtorProvider.bind: KtorClient
        @Provides get() = this
}


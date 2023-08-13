package com.mironov.di

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.mironov.common.Logger
import ru.mironov.common.ktor.KtorClient
import ru.mironov.common.ktor.KtorImpl
import ru.mironov.domain.di.Singleton
import ru.mironov.logistics.AuthApi
import ru.mironov.logistics.CitiesApi
import ru.mironov.logistics.parcel.ParcelsApi
import ru.mironov.common.ktor.source.CitiesSource
import ru.mironov.common.ktor.auth.Auth
import ru.mironov.common.ktor.source.ParcelsSource
import ru.mironov.logistics.logging.LoggerImpl
import ru.mironov.logistics.ui.navigation.ViewModelFactory

@Component
@Singleton
abstract class ApplicationComponent(
    @Component val serverContractComponent: ServerContractComponent,
    @Component val emptyComponent: EmptyComponent
) {
    abstract val factory: ViewModelFactory
    abstract val ktor: KtorClient

    companion object {
        private var instance: ApplicationComponent? = null

        private fun getInstance() = instance ?: ApplicationComponent::class
            .create(
                ServerContractComponent::class.create(),
                EmptyComponent::class.create()
            )
            .also { instance = it }
        fun getVmFactory() = getInstance().factory
        fun getKtor() = getInstance().ktor

    }
    val LoggerImpl.bind: Logger
        @Provides get() = this

    val KtorImpl.bind: KtorClient
        @Provides get() = this

}


@Component
abstract class EmptyComponent() {

}

@Component
abstract class ServerContractComponent() {

    val Auth.bind: AuthApi
        @Provides get() = this

    val ParcelsSource.bind: ParcelsApi
        @Provides get() = this

    val CitiesSource.bind: CitiesApi
        @Provides get() = this

    val KtorImpl.bind: KtorClient
        @Provides get() = this
}


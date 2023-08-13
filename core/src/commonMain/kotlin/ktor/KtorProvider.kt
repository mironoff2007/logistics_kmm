package ru.mironov.common.ktor

import io.ktor.client.HttpClient
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.ktor.client.ClientBuilderImpl
import ru.mironov.common.ktor.client.KtorClient
import ru.mironov.common.ktor.client.TestClientBuilder
import ru.mironov.domain.di.NetworkScope

@NetworkScope
class KtorProvider @Inject constructor(
    private val clientBuilder: ClientBuilderImpl,
    private val testClientBuilder: TestClientBuilder
): KtorClient {

    private var isTest = false

    override fun addNextResponse(json: String) {
        isTest = true
        testClientBuilder.addNextResponse(json)
    }

    override fun getKtorClient(log: (String) -> Unit): HttpClient =
        if (!isTest) clientBuilder.getKtorClient(log)
        else testClientBuilder.getKtorClient(log)

}
package ru.mironov.common.ktor

import io.ktor.client.HttpClient
import me.tatarka.inject.annotations.Inject
import ru.mironov.domain.di.NetworkScope

@NetworkScope
class KtorProvider @Inject constructor(
    private val clientBuilder: ClientBuilderImpl,
    private val testClientBuilder: TestClientBuilder
): KtorClient {

    private var isTest = false

    override fun addNextResponse(json: String) {
        testClientBuilder.addNextResponse(json)
    }

    override fun setTestMode() {
        isTest = true
    }

    override fun getKtorClient(log: (String) -> Unit): HttpClient =
        if (!isTest) clientBuilder.getKtorClient(log)
        else testClientBuilder.getKtorClient(log)

}
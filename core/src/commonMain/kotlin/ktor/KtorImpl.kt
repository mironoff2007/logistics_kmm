package ru.mironov.common.ktor

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.mironov.domain.di.NetworkScope

@NetworkScope
class KtorImpl @Inject constructor(): KtorClient {

    private var isTest = false
    private val mockBuilder = TestClientBuilder()
    override fun addNextResponse(json: String) {
        mockBuilder.addNextResponse(json)
    }

    override fun setTestMode() {
        isTest = true
    }

    override fun getKtorClient(log: (String)-> Unit): HttpClient =
        if (!isTest) getProdKtorClient(log)
        else mockBuilder.getKtorClient(log)

    private fun getProdKtorClient(log: (String)-> Unit) = HttpClient(HttpEngineFactory().createEngine()) {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                   log.invoke(message)
                }
            }
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        install(DefaultRequest)

        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }

        install(HttpTimeout) {
            connectTimeoutMillis = 15000
            requestTimeoutMillis = 30000
        }

        install(ResponseObserver) {
            onResponse { response ->
                log.invoke(response.toString())
                log.invoke(response.bodyAsText())
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        defaultRequest {
            url(getBaseUrl())
        }
    }

}
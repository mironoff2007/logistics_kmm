package ru.mironov.common.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
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
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.mironov.domain.di.Singleton

@Singleton
@Inject
class TestClientBuilder(): ClientBuilder {

    private val responseQueue = ArrayDeque<String>()
    fun addNextResponse(json: String) {
        responseQueue.add(json)
    }

    private val mockEngine = MockEngine { request ->
        respond(
            content = ByteReadChannel(responseQueue.firstOrNull() ?: ""),
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    override fun getKtorClient(log: (String) -> Unit): HttpClient = HttpClient(mockEngine) {
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
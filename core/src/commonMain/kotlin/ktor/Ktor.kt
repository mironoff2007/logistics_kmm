package ru.mironov.common.ktor

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object Ktor {
    fun getKtorClient(log: (String)-> Unit) = HttpClient(HttpEngineFactory().createEngine()) {
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
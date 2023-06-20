package ru.mironov.common.ktor

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class HelloApi() {
    private val client: HttpClient = Ktor.getKtorClient({})
    suspend fun getHello(): String = client.get("/").bodyAsText()

}
package ru.mironov.common.ktor

import io.ktor.client.HttpClient

interface KtorClient {

    fun getKtorClient(log: (String)-> Unit): HttpClient

    /**
     * TestOnly
     */
    fun addNextResponse(json: String)

    /**
     * TestOnly
     */
    fun setTestMode()
}
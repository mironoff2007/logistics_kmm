package ru.mironov.common.ktor.client

import io.ktor.client.HttpClient

interface KtorClient {

    fun getKtorClient(log: (String)-> Unit): HttpClient

    /**
     * Android Test Only
     */
    fun addNextResponse(json: String)

}
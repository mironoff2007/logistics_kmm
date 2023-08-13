package ru.mironov.common.ktor

import io.ktor.client.HttpClient

interface ClientBuilder {

    fun getKtorClient(log: (String)-> Unit): HttpClient
}
package ru.mironov.common.ktor.client

import io.ktor.client.HttpClient

interface ClientBuilder {

    fun getKtorClient(log: (String)-> Unit): HttpClient
}
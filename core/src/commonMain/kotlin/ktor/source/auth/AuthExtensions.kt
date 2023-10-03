package ru.mironov.common.ktor.source.auth

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import ru.mironov.common.ktor.WebConstants

fun HttpRequestBuilder.addAuth(token: CharArray?) =
    headers {
        val tokenString = token?.joinToString(separator = "") ?: ""
        append(HttpHeaders.Authorization, "${WebConstants.BEARER} $tokenString")
    }
package ru.mironov.common.ktor

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.mironov.domain.model.Result
import ru.mironov.logistics.ErrorResponse

suspend inline fun <reified T> HttpResponse.toResult(): Result<T> =
    if (this.status == HttpStatusCode.OK) {
        try {
            Result.Success(Json.decodeFromString(this.bodyAsText()))
        } catch (e: Exception) {
            Result.Error(e)
        }
    } else {
        val errorBody = try {
            this.body() as ErrorResponse
        } catch (e: Exception) {
            ErrorResponse.empty()
        }
        Result.HttpError(this.status.value, errorBody)
    }


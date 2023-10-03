package ru.mironov.common.ktor

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.mironov.domain.model.Result
import ru.mironov.logistics.ErrorResponse

object HttpResult {
    suspend inline fun <reified T> toResult(response: HttpResponse): Result<T> {
        return if (response.status == HttpStatusCode.OK) {
            try {
                Result.Success(Json.decodeFromString(response.bodyAsText()))
            } catch (e: Exception) {
                Result.Error(e)
            }
        } else {
            val errorBody = try {
                response.body() as ErrorResponse
            } catch (e: Exception) {
                ErrorResponse.empty()
            }
            Result.HttpError(response.status.value, errorBody)
        }
    }
}

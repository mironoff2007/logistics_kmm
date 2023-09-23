package ru.mironov.domain.model

import ru.mironov.logistics.ErrorResponse

sealed class Res<T> {
    data class Success<T>(val value: T) : Res<T>()
    data class HttpError<T>(val code: Int, val error: ErrorResponse) : Res<T>()
    data class Error<T>(val exception: Exception) : Res<T>()
}

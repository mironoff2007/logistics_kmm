package ru.mironov.domain.model

import ru.mironov.logistics.ErrorResponse

sealed class Result<T> {
    data class Success<T>(val value: T) : Result<T>()
    data class HttpError<T>(val code: Int, val error: ErrorResponse) : Result<T>()
    data class Error<T>(val exception: Exception) : Result<T>()

}

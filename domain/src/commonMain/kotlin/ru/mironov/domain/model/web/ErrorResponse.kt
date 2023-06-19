package ru.mironov.domain.model.web

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: Int,
    val msg: String,
)

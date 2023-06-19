package ru.mironov.domain.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResp(
    @SerialName("value")
    val token: String,
    val expireAt: Long
)
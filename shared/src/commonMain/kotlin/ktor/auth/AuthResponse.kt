package ru.mironov.common.ktor.auth

import kotlinx.serialization.Serializable
import ru.mironov.domain.model.auth.Token
import ru.mironov.domain.model.auth.TokenResp

@Serializable
data class AuthResponse(
    val token: TokenResp
)

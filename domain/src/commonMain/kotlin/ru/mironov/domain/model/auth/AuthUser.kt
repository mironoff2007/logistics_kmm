package ru.mironov.domain.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthUser(
    val username: String,
    val password: String
)

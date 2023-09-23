package ru.mironov.common.ktor.auth

import ru.mironov.domain.model.Res
import ru.mironov.logistics.auth.AuthRequest
import ru.mironov.domain.model.auth.Token

interface AuthApi {
    suspend fun signIn(user: AuthRequest): Res<Token?>
    suspend fun signUp(user: AuthRequest)
    suspend fun auth(token: String): String
}
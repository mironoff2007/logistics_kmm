package ru.mironov.common.ktor.source.auth

import ru.mironov.domain.model.Result
import ru.mironov.logistics.auth.AuthRequest
import ru.mironov.logistics.auth.AuthResponse

interface AuthApi {
    suspend fun signIn(user: AuthRequest): Result<AuthResponse?>
    suspend fun signUp(user: AuthRequest)
}
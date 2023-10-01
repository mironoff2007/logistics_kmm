package ru.mironov.common.ktor.auth

import ru.mironov.domain.model.Res
import ru.mironov.logistics.auth.AuthRequest
import ru.mironov.domain.model.auth.Token
import ru.mironov.logistics.auth.AuthResponse

interface AuthApi {
    suspend fun signIn(user: AuthRequest): Res<AuthResponse?>
    suspend fun signUp(user: AuthRequest)
}
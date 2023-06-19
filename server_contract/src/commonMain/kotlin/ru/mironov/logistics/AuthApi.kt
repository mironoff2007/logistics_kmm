package ru.mironov.logistics

import ru.mironov.domain.model.Res
import ru.mironov.domain.model.auth.AuthUser
import ru.mironov.domain.model.auth.Token

interface AuthApi {
    suspend fun signIn(user: AuthUser): Res<Token?>
    suspend fun signUp(user: AuthUser)
    suspend fun auth(token: String): String
}
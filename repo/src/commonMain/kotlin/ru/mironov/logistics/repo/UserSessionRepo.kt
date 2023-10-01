package ru.mironov.logistics.repo

import kotlinx.datetime.Clock
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.ktor.auth.Auth
import ru.mironov.domain.di.AppScope
import ru.mironov.domain.model.Res
import ru.mironov.logistics.auth.AuthRequest
import ru.mironov.domain.model.auth.Token
import ru.mironov.logistics.UserRole

@Inject
@AppScope
class UserSessionRepo(
    private val auth: Auth,
    private val logger: Logger
) {

    private var login: CharArray? = null
    private var password: CharArray? = null
    private var role: UserRole? = null
    private var token: Token? = null

    suspend fun login(userName: String, password: String): Res<Token?> {
        logger.logD(LOG_TAG, "login")
        val result = auth.signIn(AuthRequest(username = userName, password = password))
        return when (result) {
            is Res.Success -> {
                val result = result.value
                logger.logD(LOG_TAG, "call new token success")

                this.password = password.toCharArray()
                this.login = userName.toCharArray()
                this.role = result?.userData?.role

                val tokenValue = result?.token?.value?.toCharArray() ?: charArrayOf()
                this.token = Token(tokenValue, result?.token?.expireAt ?: 0)
                Res.Success(token)
            }
            is Res.Error -> {
                Res.Error(result.exception)
            }
            is Res.HttpError -> {
                Res.HttpError(result.code, result.error)
            }
        }
    }

    suspend fun getOrRefreshToken(): Token? {
        val time = Clock.System.now().toEpochMilliseconds()
        return if (token != null && (token?.expireAt ?: 0) > time) {
            logger.logD(LOG_TAG, "login, token is actual")
            token
        } else {
            logger.logD(LOG_TAG, "get new token")
            if (login != null && password != null) {
                val loginResult = login(
                    login.contentToString(),
                    password.contentToString()
                )
                if (loginResult is Res.Success) {
                    loginResult.value
                }
                else null
            }
            else {
                logger.logD(LOG_TAG, "no credentials")
                null
            }
        }
    }

    fun getRole() = role

    fun logout() {
        login = null
        password = null
        token = null
    }

    companion object {
        private const val LOG_TAG = "LoginRepo"
    }
}
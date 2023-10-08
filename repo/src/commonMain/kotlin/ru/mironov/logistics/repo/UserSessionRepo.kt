package ru.mironov.logistics.repo

import kotlinx.datetime.Clock
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.ktor.source.auth.Auth
import ru.mironov.domain.di.AppScope
import ru.mironov.domain.model.Result
import ru.mironov.logistics.auth.AuthRequest
import ru.mironov.domain.model.auth.Token
import ru.mironov.logistics.auth.UserData

@Inject
@AppScope
class UserSessionRepo(
    private val auth: Auth,
    private val logger: Logger
) {

    private var login: CharArray? = null
    private var password: CharArray? = null
    private var userData: UserData? = null
    private var token: Token? = null

    suspend fun login(userName: String, password: String): Result<Boolean> =
        when (val result = getTokenWithResult(userName, password)) {
            is Result.Success -> Result.Success(true)

            is Result.Error -> Result.Error(result.exception)

            is Result.HttpError -> Result.HttpError(result.code, result.error)
        }

    private suspend fun getTokenWithResult(userName: String, password: String): Result<Token?> {
        logger.logD(LOG_TAG, "login")
        return when (val result =
            auth.signIn(AuthRequest(username = userName, password = password))) {
            is Result.Success -> {
                val result = result.value
                logger.logD(LOG_TAG, "call new token success")

                this.password = password.toCharArray()
                this.login = userName.toCharArray()
                this.userData = result?.userData

                logger.logD(LOG_TAG, "userData-${result?.userData}")

                val tokenValue = result?.token?.value?.toCharArray() ?: charArrayOf()
                this.token = Token(tokenValue, result?.token?.expireAt ?: 0)
                Result.Success(token)
            }

            is Result.Error -> Result.Error(result.exception)

            is Result.HttpError -> Result.HttpError(result.code, result.error)
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
                val loginResult = getTokenWithResult(
                    login.contentToString(),
                    password.contentToString()
                )
                if (loginResult is Result.Success) {
                    loginResult.value
                } else null
            } else {
                logger.logD(LOG_TAG, "no credentials")
                null
            }
        }
    }

    fun getRole() = userData?.role

    fun logout() {
        login = null
        password = null
        token = null
    }

    companion object {
        private const val LOG_TAG = "LoginRepo"
    }
}
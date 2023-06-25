package ru.mironov.logistics.repo

import kotlinx.datetime.Clock
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.ktor.auth.Auth
import ru.mironov.domain.model.Res
import ru.mironov.domain.model.auth.AuthUser
import ru.mironov.domain.model.auth.Token

@Inject
class LoginRepo(
    private val auth: Auth,
    private val logger: Logger
) {

    private var token: CharArray? = null
    private var expireAt: Long? = null

    private var login: CharArray? = null
    private var password: CharArray? = null
    suspend fun auth(token: String) = auth.auth(token)

    suspend fun login(userName: String, password: String): Res<Token?> {
        logger.logD(LOG_TAG, "login")
        return auth.signIn(AuthUser(username = userName, password = password)).also {
            if (it is Res.Success) {
                logger.logD(LOG_TAG, "call new token success")
                token = it.value?.token
                expireAt = it.value?.expireAt
                this.password = password.toCharArray()
                this.login = userName.toCharArray()
            } else {
                logger.logD(LOG_TAG, "login failed")
            }
        }
    }

    suspend fun getOrRefreshToken(): Res<Token?> {
        val time = Clock.System.now().toEpochMilliseconds()
        return if (token != null && (expireAt ?: 0) > time) {
            logger.logD(LOG_TAG, "login, token is actual")
            Res.Success(
                Token(
                    token = token!!,
                    expireAt = expireAt ?: 0
                )
            )
        } else {
            logger.logD(LOG_TAG, "get new token")
            if (login != null && password != null) login(
                login.contentToString(),
                password.contentToString()
            )
            else {
                logger.logD(LOG_TAG, "no credentials")
                Res.Error(Exception("no credentials"))
            }
        }
    }

    fun logout() {
        login = null
        password = null
        token = null
        expireAt = null
    }

    companion object {
        private const val LOG_TAG = "LoginRepo"
    }

}
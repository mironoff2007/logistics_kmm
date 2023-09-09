package ru.mironov.common.ktor.auth

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.ktor.client.KtorClient
import ru.mironov.common.ktor.WebConstants.BEARER
import ru.mironov.domain.model.Res
import ru.mironov.domain.model.auth.AuthUser
import ru.mironov.domain.model.auth.Token

@Inject
class Auth(
    private val logger: Logger,
    private val ktor: KtorClient
) : AuthApi {

    private val log = fun(msg: String) = logger.logD(LOG_TAG, msg)
    private val client: HttpClient =  ktor.getKtorClient(log)

    override suspend fun auth(token: String): String = client.get("/authenticate") {
        headers {
            append(HttpHeaders.Authorization, "$BEARER $token")
        }
    }.bodyAsText()

    override suspend fun signIn(user: AuthUser): Res<Token?> {
        return try {
            client.post("/signin") {
                contentType(ContentType.Application.Json)
                setBody(user)
            }.let {
                when (it.status) {
                    HttpStatusCode.OK -> Res.Success(
                        Token (
                            token = it.body<AuthResponse>().token.token.toCharArray(),
                            expireAt = it.body<AuthResponse>().token.expireAt
                        )
                    )
                    else -> Res.HttpError(
                        code = it.status.value,
                        error = it.body()
                    )
                }
            }
        } catch (e: Exception) {
            logger.logE(LOG_TAG, e.stackTraceToString())
            Res.Error(e)
        }
    }

    override suspend fun signUp(user: AuthUser) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val LOG_TAG = "AuthApi"
    }

}
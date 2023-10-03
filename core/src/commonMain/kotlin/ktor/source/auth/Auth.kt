package ru.mironov.common.ktor.source.auth

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.ktor.HttpResult
import ru.mironov.common.ktor.client.KtorClient
import ru.mironov.domain.model.Result
import ru.mironov.logistics.auth.AuthRequest
import ru.mironov.logistics.auth.AuthResponse

@Inject
class Auth(
    private val logger: Logger,
    private val ktor: KtorClient
) : AuthApi {

    private val log = fun(msg: String) = logger.logD(LOG_TAG, msg)
    private val client: HttpClient =  ktor.getKtorClient(log)

    override suspend fun signIn(user: AuthRequest): Result<AuthResponse?> {
        return try {
            client.post("/signin") {
                contentType(ContentType.Application.Json)
                setBody(user)
            }.let { response ->
                HttpResult.toResult(response)
            }
        } catch (e: Exception) {
            logger.logE(LOG_TAG, e.stackTraceToString())
            Result.Error(e)
        }
    }

    override suspend fun signUp(user: AuthRequest) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val LOG_TAG = "AuthApi"
    }
}
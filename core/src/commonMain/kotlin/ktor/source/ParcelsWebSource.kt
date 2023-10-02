package ru.mironov.common.ktor.source

import androidx.compose.foundation.gestures.rememberScrollableState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.ktor.auth.Auth
import ru.mironov.common.ktor.auth.addAuth
import ru.mironov.common.ktor.client.KtorClient
import ru.mironov.domain.model.Result
import ru.mironov.domain.model.auth.Token
import ru.mironov.logistics.ErrorResponse
import ru.mironov.logistics.parcel.SearchResponse
import ru.mironov.logistics.parcel.SearchResponse.Companion.SEARCH_FROM_CITY_TAG
import ru.mironov.logistics.parcel.SearchResponse.Companion.SEARCH_QUERY_TAG
import ru.mironov.logistics.parcel.SearchResponse.Companion.SEARCH_TO_CITY_TAG
import ru.mironov.logistics.parcel.ServerParcel

@Inject
class ParcelsWebSource(
    private val auth: Auth,
    private val ktor: KtorClient,
    private val logger: Logger
) {

    private val log = fun(msg: String) = logger.logD(LOG_TAG, msg)
    private val client: HttpClient = ktor.getKtorClient(log)

    suspend fun registerParcels(token: Token?, parcels: List<ServerParcel>): Boolean {
        return try {
            val response = client.post("/registerParcels") {
                this.addAuth(token?.tokenValue)
                contentType(ContentType.Application.Json)
                setBody(parcels)
            }
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            log.invoke(e.stackTraceToString())
            false
        }
    }

    suspend fun searchParcels(
        token: Token?,
        searchBy: String,
        fromCityId: String,
        toCityId: String
    ): Result<SearchResponse?> {
        return try {
            val response = client.get("/searchParcels") {
                this.addAuth(token?.tokenValue)
                url {
                    parameters.append(SEARCH_QUERY_TAG, searchBy)
                    parameters.append(SEARCH_FROM_CITY_TAG, fromCityId)
                    parameters.append(SEARCH_TO_CITY_TAG, toCityId)
                }
            }
            if (response.status == HttpStatusCode.OK) Result.Success(Json.decodeFromString(response.bodyAsText()))
            else {
                val errorBody = try {
                    response.body() as ErrorResponse
                } catch (e: Exception) {
                    ErrorResponse.empty()
                }
                Result.HttpError(response.status.value, errorBody)
            }

        } catch (e: Exception) {
            log.invoke(e.stackTraceToString())
            Result.Error(e)
        }
    }

    companion object {
        private const val LOG_TAG = "ParcelsSource"
    }
}
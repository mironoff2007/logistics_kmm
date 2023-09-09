package ru.mironov.common.ktor.source

import io.ktor.client.HttpClient
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
import ru.mironov.common.ktor.client.KtorClient
import ru.mironov.logistics.parcel.SearchResponse
import ru.mironov.logistics.parcel.SearchResponse.Companion.SEARCH_QUERY_TAG
import ru.mironov.logistics.parcel.ServerParcel

@Inject
class ParcelsWebSource(
    private val logger: Logger,
    private val ktor: KtorClient
) {

    private val log = fun(msg: String) = logger.logD(LOG_TAG, msg)
    private val client: HttpClient = ktor.getKtorClient(log)

    suspend fun registerParcels(parcels: List<ServerParcel>): Boolean {
        return try {
            val response = client.post("/registerParcels") {
                contentType(ContentType.Application.Json)
                setBody(parcels)
            }
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            log.invoke(e.stackTraceToString())
            false
        }
    }

    suspend fun searchParcels(searchBy: String): SearchResponse {
        return try {
            val response = client.get("/searchParcels") {
                url {
                    parameters.append(SEARCH_QUERY_TAG, searchBy)
                }
            }
            Json.decodeFromString(response.bodyAsText())
        } catch (e: Exception) {
            log.invoke(e.stackTraceToString())
            SearchResponse(0, emptyList())
        }
    }

    companion object {
        private const val LOG_TAG = "ParcelsSource"
    }
}
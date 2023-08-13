package ru.mironov.common.ktor.source

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.ktor.client.KtorClient
import ru.mironov.logistics.parcel.ParcelsApi
import ru.mironov.logistics.parcel.ServerParcel

@Inject
class ParcelsSource(
    private val logger: Logger,
    private val ktor: KtorClient
) : ParcelsApi {

    private val log = fun(msg: String) = logger.logD(LOG_TAG, msg)
    private val client: HttpClient = ktor.getKtorClient(log)
    override suspend fun registerParcels(parcels: List<ServerParcel>): Boolean {
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

    companion object {
        private const val LOG_TAG = "ParcelsApi"
    }

}
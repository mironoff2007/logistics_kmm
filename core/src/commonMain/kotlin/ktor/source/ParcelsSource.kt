package ru.mironov.common.ktor.source

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.mironov.domain.model.Parcel
import ru.mironov.logistics.ParcelsApi
import ru.mironov.common.ktor.Ktor
import ru.mironov.common.Logger

@Inject
class ParcelsSource(private val logger: Logger) : ParcelsApi {

    private val log = fun(msg: String) = logger.logD(LOG_TAG, msg)
    private val client: HttpClient = Ktor.getKtorClient(log)
    override suspend fun registerParcels(parcels: List<Parcel>): Boolean {
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
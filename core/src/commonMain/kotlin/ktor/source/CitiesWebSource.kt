package ru.mironov.common.ktor.source

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.ktor.client.KtorClient
import ru.mironov.domain.model.City
import ru.mironov.domain.model.toCity
import ru.mironov.logistics.ServerCity

@Inject
class CitiesWebSource(
    ktor: KtorClient,
    private val logger: Logger
) {

    private val log = fun(msg: String) = logger.logD(LOG_TAG, msg)
    private val client: HttpClient = ktor.getKtorClient(log)

    suspend fun fetchCities(): List<City> {
        return try {
            val response = client.get("/cities")
            val serverCities: List<ServerCity> = Json.decodeFromString(response.bodyAsText())
            serverCities.map { it.toCity() }
        }
        catch (e: Exception) {
            emptyList()
        }
    }

    companion object {
        private const val LOG_TAG = "CitiesWebSource"
    }
}
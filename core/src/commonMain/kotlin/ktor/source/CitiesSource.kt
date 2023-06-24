package ru.mironov.common.ktor.source

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.mironov.domain.model.City
import ru.mironov.logistics.CitiesApi
import ru.mironov.common.ktor.Ktor
import ru.mironov.common.Logger

@Inject
class CitiesSource(private val logger: Logger): CitiesApi {

    private val log = fun(msg: String) = logger.logD(LOG_TAG, msg)
    private val client: HttpClient = Ktor.getKtorClient(log)
    override suspend fun fetchCities(): List<City> {
        return try {
            val response = client.get("/cities")
            Json.decodeFromString(response.bodyAsText())
        }
        catch (e: Exception) {
            emptyList()
        }
    }

    companion object {
        private const val LOG_TAG = "CitiesApi"
    }

}
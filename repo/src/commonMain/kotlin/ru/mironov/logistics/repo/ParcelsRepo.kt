package ru.mironov.logistics.repo

import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.ktor.source.ParcelsWebSource
import ru.mironov.domain.model.Parcel
import ru.mironov.logistics.parcel.ServerParcel

@Inject
class ParcelsRepo(
    private val parcelsWebSource: ParcelsWebSource,
    private val logger: Logger,
) {

    suspend fun searchParcels(
        searchBy: String,
        fromCityId: String,
        toCityId: String
    ): List<Parcel> {
        val response = parcelsWebSource.searchParcels(
            searchBy = searchBy,
            fromCityId = fromCityId,
            toCityId = toCityId
        )
        return response.parcels.map { it.toParcel() }
    }
    companion object {
        private const val LOG_TAG = "ParcelsRepo"
    }
}
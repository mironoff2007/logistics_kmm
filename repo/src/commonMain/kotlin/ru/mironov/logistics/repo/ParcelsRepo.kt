package ru.mironov.logistics.repo

import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.ktor.source.ParcelsWebSource
import ru.mironov.domain.model.Parcel
import ru.mironov.domain.model.Result
import ru.mironov.logistics.parcel.ServerParcel

@Inject
class ParcelsRepo(
    private val userSessionRepo: UserSessionRepo,
    private val parcelsWebSource: ParcelsWebSource,
    private val logger: Logger,
) {

    suspend fun searchParcels(
        searchBy: String,
        fromCityId: String,
        toCityId: String
    ): List<Parcel> {
        val response = parcelsWebSource.searchParcels(
            token = userSessionRepo.getOrRefreshToken(),
            searchBy = searchBy,
            fromCityId = fromCityId,
            toCityId = toCityId
        )
        return when (response) {
            is Result.Success -> response.value?.parcels?.map { it.toParcel() } ?: emptyList()
            is Result.HttpError -> emptyList()
            is Result.Error -> emptyList()
        }
    }

    companion object {
        private const val LOG_TAG = "ParcelsRepo"
    }
}
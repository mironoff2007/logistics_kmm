package ru.mironov.common.ktor.source

import ru.mironov.logistics.parcel.SearchResponse
import ru.mironov.logistics.parcel.ServerParcel

interface ParcelsApi {
    suspend fun registerParcels(parcels: List<ServerParcel>): Boolean
    suspend fun searchParcels(searchBy: String): SearchResponse
}
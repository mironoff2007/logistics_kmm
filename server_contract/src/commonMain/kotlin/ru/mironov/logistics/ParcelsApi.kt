package ru.mironov.logistics

import ru.mironov.domain.model.Parcel

interface ParcelsApi {
    suspend fun registerParcels(parcels: List<Parcel>): Boolean
}
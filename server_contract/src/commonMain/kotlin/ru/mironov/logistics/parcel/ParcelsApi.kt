package ru.mironov.logistics.parcel

interface ParcelsApi {
    suspend fun registerParcels(parcels: List<ServerParcel>): Boolean
}
package com.mironov.database.parcel

import me.tatarka.inject.annotations.Inject
import ru.mironov.common.data.sqlitedb.CityDb
import ru.mironov.common.data.sqlitedb.ParcelDb
import ru.mironov.common.data.toCity
import ru.mironov.common.data.toParcel
import ru.mironov.domain.model.Parcel


@Inject
class ParcelsDbSource(
    private val parcelDb: ParcelDb,
    private val cityDb: CityDb
) {
    
    fun clear() {
        parcelDb.drop()
        cityDb.drop()
    }

    fun replace(parcel: Parcel) {
        parcelDb.replace(parcel)
    }

    fun replaceAllTransaction(parcels: List<Parcel>) {
        parcelDb.replaceAll(parcels)
    }
    
    fun fetchAll(): List<Parcel> {
        val parcelsEntities = parcelDb.getAllParcels()
        val cities = cityDb.getAllCities().executeAsList().map { it.toCity() }
        return parcelsEntities.executeAsList().map { it.toParcel(cities) }
    }

    
    fun get(id: Long): Parcel? {
        val cities = cityDb.getAllCities().executeAsList().map { it.toCity() }
        return parcelDb.get(id).executeAsOneOrNull()?.toParcel(cities)
    }

    
    fun fetchNotSynced(): List<Parcel> {
        val cities = cityDb.getAllCities().executeAsList().map { it.toCity() }
        return parcelDb.getNotSynced().executeAsList().map { it.toParcel(cities) }
    }
    fun fetchSynced(): List<Parcel> {
        val cities = cityDb.getAllCities().executeAsList().map { it.toCity() }
        return parcelDb.getSynced().executeAsList().map { it.toParcel(cities) }
    }

    fun delete(parcel: Parcel) =
        parcelDb.delete(parcel.parcelId)

    fun delete(parcels: List<Parcel>) =
        parcelDb.delete(parcels)

}








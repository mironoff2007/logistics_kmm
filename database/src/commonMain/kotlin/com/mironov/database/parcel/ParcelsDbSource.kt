package com.mironov.database.parcel

import me.tatarka.inject.annotations.Inject
import ru.mironov.common.data.CityDb
import ru.mironov.common.data.ParcelDb
import ru.mironov.common.data.toCity
import ru.mironov.common.data.toParcel
import ru.mironov.domain.model.City
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

    
    fun inTransaction(method: () -> Unit) {

    }

    
    fun insertAllBatch(parcels: List<Parcel>) {

    }

    
    fun replaceAllTransaction(parcels: List<Parcel>) {
        parcelDb.replaceAll(parcels)
    }

    
    fun count(): Int {
        var count = 0

        return count
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

    
    fun selectSearch(
        search: String,
        currentCitySearch: City?,
        destinationCitySearch: City?
    ): List<Parcel> {
        return fetchAll() //todo
    }


}








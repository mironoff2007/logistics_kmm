package com.mironov.database.city

import me.tatarka.inject.annotations.Inject
import ru.mironov.common.data.CityDb
import ru.mironov.common.data.toCity
import ru.mironov.domain.model.City


@Inject
class CityTable(private val cityDb: CityDb) {


    fun clear() {
        cityDb.drop()
    }

    
    fun replace(cityEntity: City) {
        cityDb.replace(cityEntity)
    }

    
    fun replaceAll(cities: List<City>) {
        cityDb.replaceAll(cities)
    }

    
    fun count(): Int {
        var count = 0

        return count
    }

    
    fun fetchAll(): List<City> {
        return cityDb.getAllCities().executeAsList().map { it.toCity() }
    }

    
    fun get(id: Int): City {
        return cityDb.getById(id.toLong()).executeAsOne().toCity()
    }

}


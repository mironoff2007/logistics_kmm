package ru.mironov.logistics.repo

import com.mironov.database.city.CityDbSource
import me.tatarka.inject.annotations.Inject
import ru.mironov.domain.model.City
import ru.mironov.logistics.CitiesApi

@Inject
class CityRepo(
    private val citySource: CitiesApi,
    private val cityTable: CityDbSource
) {

    suspend fun fetchCities(): List<City> {
        val cities = cityTable.fetchAll()
        if (cities.isEmpty()) {
            val citiesFromServer = citySource.fetchCities()
            cityTable.replaceAll(citiesFromServer)
        }
        return cityTable.fetchAll()
    }

}
package ru.mironov.common.data

import app.cash.sqldelight.Query
import app.cash.sqldelight.db.SqlDriver
import data.CityEntity
import data.CityQueries
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.data.Constants.DB_NAME
import ru.mironov.domain.model.City

@Inject
class CityDb() {

    private val driver: SqlDriver = DriverFactory().createDriver(DB_NAME)
    private val cityQueries = CityQueries(driver)

    fun drop() {
        cityQueries.drop()
    }

    fun getAllCities(): Query<CityEntity> {
        return cityQueries.selectAll()
    }

    fun replace(city: City) {
        cityQueries.replace(
            cityId = city.id.toLong(),
            cityName = city.name
        )
    }

    fun replaceAll(list: List<City>) {
        cityQueries.transaction { list.forEach { replace(it) } }
    }

    fun getById(id: Long): Query<CityEntity> {
        return cityQueries.getById(id)
    }

}


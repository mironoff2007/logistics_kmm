package ru.mironov.common.ktor.source

import ru.mironov.domain.model.City

interface CitiesApi {
    suspend fun fetchCities(): List<City>
}
package ru.mironov.logistics

import ru.mironov.domain.model.City

interface CitiesApi {
    suspend fun fetchCities(): List<City>
}
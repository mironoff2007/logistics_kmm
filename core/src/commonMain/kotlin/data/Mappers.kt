package ru.mironov.common.data

import data.CityEntity
import data.ParcelEntity
import ru.mironov.domain.model.City
import ru.mironov.domain.model.Parcel


fun CityEntity.toCity(): City {
    return City(
        id = this.cityId.toInt(),
        name = this.cityName
    )
}

fun ParcelEntity.toParcel(cities: List<City>): Parcel {
    return Parcel(
        parcelId = this.parcelId,
        customerName = this.customerName ?: "",
        customerSecondName = this.customerSecondName ?: "",
        address = this.address ?: "",
        senderName = this.senderName ?: "",
        senderSecondName = this.senderSecondName ?: "",
        senderAddress = this.senderAddress ?: "",
        destinationCity = cities.firstOrNull { it.name == this.destinationCity }!!,
        senderCity = cities.firstOrNull { it.name ==  this.senderCity }!!,
        currentCity =  cities.firstOrNull { it.name == this.currentCity }!!,
        dateShow = this.dateShow ?: "",
        dateMillis = this.date ?: 0L,
        synced = this.synced == "true"
    )
}

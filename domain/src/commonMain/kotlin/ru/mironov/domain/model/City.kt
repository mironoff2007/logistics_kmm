package ru.mironov.domain.model


import kotlinx.serialization.Serializable
import ru.mironov.logistics.ServerCity

@Serializable
data class City (val id: Int, val name:String) {


}

fun ServerCity.toCity(): City {
    return City(
        id = this.id,
        name = this.name
    )
}

fun City.toServerCity(): ServerCity {
    return ServerCity(
        id = this.id,
        name = this.name
    )
}
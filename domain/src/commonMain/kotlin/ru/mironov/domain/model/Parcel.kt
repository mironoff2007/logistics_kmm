package ru.mironov.domain.model

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Parcel(
    val parcelId: Long,
    val customerName: String,
    val customerSecondName: String,
    val address: String,
    val senderName: String = "",
    val senderSecondName: String = "",
    val senderAddress: String = "",
    val destinationCity: City,
    val currentCity: City,
    val senderCity: City,
    val dateShow: String = Date().toString(),
    val date: Long = Date().time,
    val synced: Boolean = false
) {
    fun asSynced() = this.copy(synced = true)

}
package ru.mironov.domain.model

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

@Serializable
data class Parcel(
    val parcelId: Long,
    val storeId: Long,
    val customerName: String,
    val customerSecondName: String,
    val address: String,
    val senderName: String = "",
    val senderSecondName: String = "",
    val senderAddress: String = "",
    val destinationCity: City,
    val currentCity: City,
    val senderCity: City,
    val dateShow: String,
    val dateMillis: Long = Clock.System.now().toEpochMilliseconds(),
    val synced: Boolean = false
) {
    fun asSynced() = this.copy(synced = true)

}
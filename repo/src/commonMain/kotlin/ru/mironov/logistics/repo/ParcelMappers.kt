package ru.mironov.logistics.repo

import ru.mironov.domain.model.Parcel
import ru.mironov.logistics.parcel.ServerParcel


fun Parcel.toServerParcel(): ServerParcel {
    return ServerParcel(
        parcelId = this.parcelId,
        customerName = this.customerName,
        customerSecondName = this.customerSecondName,
        address = this.address,
        senderName = this.senderName,
        senderSecondName = this.senderSecondName,
        senderAddress = this.senderAddress,
        destinationCity = this.destinationCity,
        currentCity = this.currentCity,
        senderCity = this.senderCity,
        dateShow = this.dateShow,
        date = this.dateMillis
    )
}

fun ServerParcel.toSyncedParcel(): Parcel {
    return Parcel(
        parcelId = this.parcelId,
        customerName = this.customerName,
        customerSecondName = this.customerSecondName,
        address = this.address,
        senderName = this.senderName,
        senderSecondName = this.senderSecondName,
        senderAddress = this.senderAddress,
        destinationCity = this.destinationCity,
        currentCity = this.currentCity,
        senderCity = this.senderCity,
        dateShow = this.dateShow,
        dateMillis = this.date,
        synced = true
    )
}

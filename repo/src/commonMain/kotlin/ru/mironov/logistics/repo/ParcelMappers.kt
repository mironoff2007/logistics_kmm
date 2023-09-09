package ru.mironov.logistics.repo

import ru.mironov.domain.model.Parcel
import ru.mironov.domain.model.toCity
import ru.mironov.domain.model.toServerCity
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
        destinationCity = this.destinationCity.toServerCity(),
        currentCity = this.currentCity.toServerCity(),
        senderCity = this.senderCity.toServerCity(),
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
        destinationCity = this.destinationCity.toCity(),
        currentCity = this.currentCity.toCity(),
        senderCity = this.senderCity.toCity(),
        dateShow = this.dateShow,
        dateMillis = this.date,
        synced = true
    )
}

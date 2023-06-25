package ru.mironov.common.data

import app.cash.sqldelight.Query
import app.cash.sqldelight.db.SqlDriver
import data.ParcelEntity
import data.ParcelQueries
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.data.Constants.DB_NAME
import ru.mironov.domain.model.Parcel

@Inject
class ParcelDb() {

    private val driver: SqlDriver = DriverFactory().createDriver(DB_NAME)
    private val parcelQueries = ParcelQueries(driver)

    fun drop() {
        parcelQueries.drop()
    }

    fun get(id: Long): Query<ParcelEntity> {
        return parcelQueries.getById(id)
    }

    fun getAllParcels(): Query<ParcelEntity> {
        return parcelQueries.selectAll()
    }

    fun replace(parcel: Parcel) {
        parcelQueries.replace(
            parcelId = parcel.parcelId,
            customerName = parcel.customerName,
            customerSecondName = parcel.customerSecondName,
            address = parcel.address,
            senderName = parcel.senderName,
            senderSecondName = parcel.senderSecondName,
            senderAddress = parcel.senderAddress,
            destinationCity = parcel.destinationCity.name,
            senderCity = parcel.senderCity.name,
            currentCity = parcel.currentCity.name,
            dateShow = parcel.dateShow,
            date = parcel.dateMillis,
            synced = parcel.synced.toString()
        )
    }

    fun replaceAll(list: List<Parcel>) {
        parcelQueries.transaction { list.forEach { replace(it) } }
    }

    fun getNotSynced(): Query<ParcelEntity> {
        return parcelQueries.selectNotSynced()
    }

}


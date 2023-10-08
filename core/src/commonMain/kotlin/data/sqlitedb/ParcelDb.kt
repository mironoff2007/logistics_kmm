package ru.mironov.common.data.sqlitedb

import app.cash.sqldelight.Query
import app.cash.sqldelight.db.SqlDriver
import data.ParcelEntity
import data.ParcelQueries
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.data.Constants.DB_NAME
import ru.mironov.common.data.DriverFactory
import ru.mironov.domain.model.Parcel

@Inject
class ParcelDb() {

    private val driver: SqlDriver = DriverFactory().createDriver(DB_NAME)
    private val parcelQueries = ParcelQueries(driver)

    fun drop() = parcelQueries.drop()

    fun get(id: Long): Query<ParcelEntity> = parcelQueries.getById(id)

    fun delete(list: List<Parcel>) = parcelQueries.transaction {
        list.forEach { parcelQueries.delete(it.parcelId) }
    }

    fun delete(id: Long) = parcelQueries.delete(id)

    fun getAllParcels(): Query<ParcelEntity> = parcelQueries.selectAll()

    fun replace(parcel: Parcel) =
        parcelQueries.replace(
            parcelId = parcel.parcelId,
            storeId = parcel.storeId,
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

    fun replaceAll(list: List<Parcel>) =
        parcelQueries.transaction { list.forEach { replace(it) } }

    fun getNotSynced(): Query<ParcelEntity> =
        parcelQueries.selectNotSynced()

    fun getSynced(): Query<ParcelEntity> =
        parcelQueries.selectSynced()

}


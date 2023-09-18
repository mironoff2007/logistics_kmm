package ru.mironov.logistics.ui.screens.parceldata

import kotlinx.serialization.Serializable
import ru.mironov.domain.model.Parcel

@Serializable
class ParcelDataArg(val parcel: Parcel)
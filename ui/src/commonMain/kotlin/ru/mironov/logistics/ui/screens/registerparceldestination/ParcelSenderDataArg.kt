package ru.mironov.logistics.ui.screens.registerparceldestination

import kotlinx.serialization.Serializable
import ru.mironov.domain.model.ParcelData

@Serializable
class ParcelSenderDataArg(val data: ParcelData)
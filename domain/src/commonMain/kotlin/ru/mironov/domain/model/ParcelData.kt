package ru.mironov.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ParcelData(
    val personName: String,
    val personSecondName: String,
    val address: String,
    val city: City?
) {

    fun isCorrect(): Boolean {
        return personName.isNotBlank()
                && personSecondName.isNotBlank()
                && address.isNotBlank()
                && city != null
    }
}
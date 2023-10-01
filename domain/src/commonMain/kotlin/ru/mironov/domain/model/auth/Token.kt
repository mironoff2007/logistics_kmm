package ru.mironov.domain.model.auth


class Token(
    val tokenValue: CharArray,
    val expireAt: Long
)
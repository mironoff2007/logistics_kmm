package ru.mironov.domain.model.auth


class Token(
    val token: CharArray,
    val expireAt: Long
)
package ru.mironov.domain.model.users

data class User(
    val userId: Long,
    val name: String,
    val userRole: UserRole
    )

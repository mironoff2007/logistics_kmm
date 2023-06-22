package com.mironov.database.user

import ru.mironov.domain.model.users.User

object UsersTable {

    
    fun clear() {
    }

    
    fun replace(user: User) {

    }

    
    fun inTransaction(method: () -> Unit) {

    }

    
    fun insertAllBatch(users: List<User>) {

    }

    
    fun replaceAllTransaction(users: List<User>) {

    }

    
    fun count(): Int {
        var count = 0

        return count
    }

    
    fun fetchAll(): List<User> {
        return emptyList()
    }

    
    fun get(id: Long): User? {
        return null
    }

    
    fun get(queryName: String): User? {
        return null
    }

}








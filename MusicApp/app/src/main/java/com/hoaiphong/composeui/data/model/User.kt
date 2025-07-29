package com.hoaiphong.composeui.data.model

data class User(val username: String, val password: String, val email: String)

object UserManager {
    val users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    fun findUser(username: String): User? {
        return users.find { it.username == username }
    }

    fun validateLogin(username: String, password: String): Boolean {
        return users.any { it.username == username && it.password == password }
    }
}
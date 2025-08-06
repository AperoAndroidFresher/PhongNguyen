package com.hoaiphong.composeui.data.repository

import com.hoaiphong.composeui.data.local.model.entity.User

interface UserRepository {
    suspend fun login(username: String, password: String): User?
    suspend fun register(username: String, password: String, email: String): User
    suspend fun findUserByUsername(username: String): User
    suspend fun updateUser(
        username: String,
        fullName: String,
        phongNumber: String,
        universityName: String,
        description: String,
        imgUrl: String
    )
}

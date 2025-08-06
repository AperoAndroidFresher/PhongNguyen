package com.hoaiphong.composeui.db.repository.impl

import android.content.Context
import com.hoaiphong.composeui.db.AppDatabase
import com.hoaiphong.composeui.db.entity.User
import com.hoaiphong.composeui.db.repository.UserRepository

class UserRepositoryImpl(
    context: Context
) : UserRepository {
    val userDAO = AppDatabase.getInstance(context).userDao()
    override suspend fun login(username: String, password: String): User? {
        val user = userDAO.findByUserName(username)
        return if (user?.password == password) {
            user
        } else {
            null
        }
    }

    override suspend fun register(username: String, password: String, email: String): User {
        val existingUser = userDAO.findByUserName(username)
        if (existingUser != null) {
            throw Exception("Username already exists")
        } else {
            val user = User(
                userName = username,
                password = password,
                email = email,
                fullName = "",
                phoneNumber = "",
                universityName = "",
                description = "",
                imgUrl = ""
            )
            userDAO.insertAllUser(user)
            return user
        }
    }

    override suspend fun findUserByUsername(username: String): User {
        return userDAO.findByUserName(username)
            ?: throw Exception("User not found")
    }

    override suspend fun updateUser(
        username: String,
        fullName: String,
        phongNumber: String,
        universityName: String,
        description: String,
        imgUrl: String
    ) {
        val existingUser = userDAO.findByUserName(username)
        if (existingUser == null) {
            throw Exception("User not register")
        } else {
            val updatedUser = existingUser.copy(
                fullName = fullName,
                phoneNumber = phongNumber,
                universityName = universityName,
                description = description,
                imgUrl = imgUrl
            )
            userDAO.updateUser(updatedUser)
        }
    }

}

package com.hoaiphong.composeui.data.local.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "full_name") val fullName: String? = null,
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "phone_number") val phoneNumber: String? = null,
    @ColumnInfo(name = "university_name") val universityName: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "img_url") val imgUrl: String
)

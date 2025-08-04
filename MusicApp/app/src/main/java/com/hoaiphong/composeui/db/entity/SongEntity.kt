package com.hoaiphong.composeui.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class Song(
    @PrimaryKey val songId: Long,
    val name: String,
    val artist: String,
    val duration: Long,
    val image: String?,
    val data: String
)
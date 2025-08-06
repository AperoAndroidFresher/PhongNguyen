package com.hoaiphong.composeui.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class Song(
    @PrimaryKey val songId: Long,
    @ColumnInfo(name = "song_name") val name: String,
    @ColumnInfo(name = "song_artist") val artist: String,
    @ColumnInfo(name = "duration") val duration: Long,
    @ColumnInfo(name = "image_url") val image: String?,
    @ColumnInfo(name = "song_data") val data: String
)
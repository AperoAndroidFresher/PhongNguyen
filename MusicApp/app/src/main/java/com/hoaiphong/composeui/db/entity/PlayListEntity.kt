package com.hoaiphong.composeui.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "playlist",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_name"],
            childColumns = ["owner_username"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["owner_username"])]
)
data class Playlist(
    @PrimaryKey(autoGenerate = true) val playlistId: Long = 0,
    @ColumnInfo(name = "playlist_name") val name: String? = null,
    @ColumnInfo(name = "owner_username") val ownerUsername: String? = null
)
package com.hoaiphong.composeui.data.local.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["playlistId", "songId"],
    foreignKeys = [
        ForeignKey(
            entity = Playlist::class,
            parentColumns = ["playlistId"],
            childColumns = ["playlistId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Song::class,
            parentColumns = ["songId"],
            childColumns = ["songId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("playlistId"), Index("songId")]
)
data class PlaylistSongCrossRef(
    val playlistId: Long,
    val songId: Long
)

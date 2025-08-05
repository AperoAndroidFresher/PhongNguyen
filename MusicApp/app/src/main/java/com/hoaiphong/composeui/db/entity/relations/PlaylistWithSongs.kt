package com.hoaiphong.composeui.db.entity.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.hoaiphong.composeui.db.entity.Playlist
import com.hoaiphong.composeui.db.entity.PlaylistSongCrossRef
import com.hoaiphong.composeui.db.entity.Song

@Entity
data class PlaylistWithSongs(
    @Embedded val playlist: Playlist,
    @Relation(
        parentColumn = "playlistId",
        entity = Song::class,
        entityColumn = "songId",
        associateBy = Junction(PlaylistSongCrossRef::class)
    )
    val songs: List<Song>
)
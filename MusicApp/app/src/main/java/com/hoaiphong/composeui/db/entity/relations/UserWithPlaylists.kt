package com.hoaiphong.composeui.db.entity.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.hoaiphong.composeui.db.entity.Playlist
import com.hoaiphong.composeui.db.entity.User
@Entity
data class UserWithPlaylists(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_name",
        entityColumn = "owner_username"
    )
    val playlists: List<Playlist>
)
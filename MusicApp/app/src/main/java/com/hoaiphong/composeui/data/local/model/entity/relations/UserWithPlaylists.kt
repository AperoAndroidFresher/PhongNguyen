package com.hoaiphong.composeui.data.local.model.entity.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.hoaiphong.composeui.data.local.model.entity.Playlist
import com.hoaiphong.composeui.data.local.model.entity.User

@Entity
data class UserWithPlaylists(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_name",
        entityColumn = "owner_username"
    )
    val playlists: List<Playlist>
)

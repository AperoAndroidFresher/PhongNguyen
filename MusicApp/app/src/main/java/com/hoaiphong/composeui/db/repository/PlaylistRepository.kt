package com.hoaiphong.composeui.db.repository


import com.hoaiphong.composeui.db.entity.Playlist
import com.hoaiphong.composeui.db.entity.relations.PlaylistWithSongs

interface PlaylistRepository {
    suspend fun getAllPlaylists(): List<String> // chỉ lấy tên
    suspend fun addPlaylist(name: String)
    suspend fun removePlaylist(name: String)
    suspend fun renamePlaylist(oldName: String, newName: String)
}
package com.hoaiphong.composeui.data.repository

import com.hoaiphong.composeui.data.local.model.entity.Playlist
import com.hoaiphong.composeui.data.local.model.entity.relations.PlaylistWithSongs

interface PlaylistRepository {
    suspend fun getAllPlaylists(): List<Playlist>
    suspend fun addPlaylist(playlistName: String, user_name: String)
    suspend fun removePlaylist(name: String)
    suspend fun renamePlaylist(oldName: String, newName: String)
    suspend fun getPlaylistWithSongs(name: String): PlaylistWithSongs?
    suspend fun removeSongFromPlaylist(playlistName: String, songId: Long)
    suspend fun getSongIdsInPlaylist(playlistName: String): List<Long>
    suspend fun isSongInPlaylist(playlistName: String, songId: Long): Boolean
}

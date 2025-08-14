package com.hoaiphong.composeui.data.repository

import com.hoaiphong.composeui.data.local.SongLocal
import com.hoaiphong.composeui.data.local.model.entity.Playlist
import com.hoaiphong.composeui.data.local.model.entity.Song
import com.hoaiphong.composeui.data.local.model.entity.relations.PlaylistWithSongs
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun getAllPlaylists(): List<Playlist>
    suspend fun getPlaylistWithSongs(playlistId: Long): PlaylistWithSongs?
    suspend fun getPlaylistByName(name: String): Playlist?
    suspend fun getPlaylistWithSongsById(id: Long): PlaylistWithSongs?
    suspend fun addPlaylist(name: String, ownerUsername: String): Playlist?
    suspend fun addSongToPlaylist(playlistId: Long, song: SongLocal)
    suspend fun renamePlaylist(playlistId: Long, newName: String): Boolean
    suspend fun removePlaylist(playlistId: Long)
    suspend fun removeSongFromPlaylist(playlistId: Long, songId: Long)
    suspend fun getPlaylistsWithSongs(username: String): Flow<List<PlaylistWithSongs>>
    suspend fun getPlaylistWithSongsByName(name: String): PlaylistWithSongs?
    suspend fun getSongsByIds(songIds: List<Long>): List<Song>
}

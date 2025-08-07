package com.hoaiphong.composeui.data.repository.impl

import com.hoaiphong.composeui.data.local.room.dao.PlayListDAO
import com.hoaiphong.composeui.data.local.room.dao.PlaylistSongCrossRefDAO
import com.hoaiphong.composeui.data.local.room.dao.SongDAO
import com.hoaiphong.composeui.data.local.model.entity.Playlist
import com.hoaiphong.composeui.data.local.model.entity.PlaylistSongCrossRef
import com.hoaiphong.composeui.data.local.model.entity.relations.PlaylistWithSongs
import com.hoaiphong.composeui.data.local.SongLocal
import com.hoaiphong.composeui.data.local.toEntity
import kotlinx.coroutines.flow.Flow

class PlaylistManager(
    private val playListDAO: PlayListDAO,
    private val songDAO: SongDAO,
    private val crossRefDAO: PlaylistSongCrossRefDAO
) {

    suspend fun getAllPlaylists(): List<Playlist> {
        return playListDAO.getAllPlaylist()
    }

    suspend fun getPlaylistWithSongs(playlistId: Long): PlaylistWithSongs? {
        return playListDAO.getPlaylistWithSongs(playlistId)
    }

    suspend fun getPlaylistByName(name: String): Playlist? {
        return playListDAO.findByPlaylistName(name)
    }

    suspend fun getPlaylistWithSongsById(id: Long): PlaylistWithSongs? {
        return playListDAO.getPlaylistWithSongsById(id)
    }

    suspend fun addPlaylist(name: String, ownerUsername: String): Playlist? {
        val existing = playListDAO.findByPlaylistName(name)
        if (existing != null) return null

        val newPlaylist = Playlist(name = name, ownerUsername = ownerUsername)
        playListDAO.insertPlaylist(newPlaylist)

        return playListDAO.findByPlaylistName(name)
    }

    suspend fun addSongToPlaylist(playlistId: Long, song: SongLocal) {
        // Convert sang entity Room
        val songEntity = song.toEntity()

        // Đảm bảo bài hát đã có trong DB
        songDAO.insertSong(songEntity)

        // Thêm quan hệ playlist-song
        crossRefDAO.addSongToPlaylist(
            PlaylistSongCrossRef(
                playlistId = playlistId,
                songId = song.id
            )
        )
    }

    suspend fun renamePlaylist(playlistId: Long, newName: String) {
        playListDAO.renamePlaylist(playlistId, newName)
    }

    suspend fun removePlaylist(playlistId: Long) {
        playListDAO.deletePlaylistById(playlistId)
        crossRefDAO.deleteAllPlaylistSongCrossRefByPlaylistId(playlistId)
    }

    suspend fun removeSongFromPlaylist(playlistId: Long, songId: Long) {
        crossRefDAO.deleteByPlaylistSongCrossRefIds(playlistId, songId)
    }

    fun getPlaylistsWithSongs(): Flow<List<PlaylistWithSongs>> {
        return playListDAO.getAllPlaylistsWithSongs()
    }

    suspend fun getPlaylistWithSongsByName(name: String): PlaylistWithSongs? {
        val playlist = playListDAO.findByPlaylistName(name) ?: return null
        return getPlaylistWithSongs(playlist.playlistId)
    }
}

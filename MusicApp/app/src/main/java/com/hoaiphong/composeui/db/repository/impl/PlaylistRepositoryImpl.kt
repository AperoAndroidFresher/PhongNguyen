package com.hoaiphong.composeui.db.repository.impl

import android.content.Context
import com.hoaiphong.composeui.db.AppDatabase
import com.hoaiphong.composeui.db.entity.Playlist
import com.hoaiphong.composeui.db.entity.relations.PlaylistWithSongs
import com.hoaiphong.composeui.db.repository.PlaylistRepository

class PlaylistRepositoryImpl(context: Context) : PlaylistRepository {

    private val playlistDao = AppDatabase.getInstance(context).playListDao()
    private val crossRefDao = AppDatabase.getInstance(context).playlistSongCrossRefDAO()

    override suspend fun getAllPlaylists(): List<Playlist> {
        return playlistDao.getAllPlaylist()
    }

    override suspend fun addPlaylist(playlistName: String, userName: String) {
        val existing = playlistDao.findByPlaylistName(playlistName)
        if (existing == null) {
            val playlist = Playlist(name = playlistName, ownerUsername = userName)
            playlistDao.insertPlaylist(playlist)
        }
    }

    override suspend fun removePlaylist(name: String) {
        val playlist = playlistDao.findByPlaylistName(name)
        playlist?.let {
            crossRefDao.deleteAllPlaylistSongCrossRefByPlaylistId(it.playlistId)
            playlistDao.deletePlaylistById(it.playlistId)
        }
    }

    override suspend fun renamePlaylist(oldName: String, newName: String) {
        val oldPlaylist = playlistDao.findByPlaylistName(oldName)
        val conflict = playlistDao.findByPlaylistName(newName)
        if (oldPlaylist != null && conflict == null) {
            val updated = oldPlaylist.copy(name = newName)
            playlistDao.updatePlaylist(updated)
        }
    }

    override suspend fun getPlaylistWithSongs(name: String): PlaylistWithSongs? {
        val playlist = playlistDao.findByPlaylistName(name)
        return playlist?.let {
            playlistDao.getPlaylistWithSongs(it.playlistId)
        }
    }


    override suspend fun removeSongFromPlaylist(playlistName: String, songId: Long) {
        val playlist = playlistDao.findByPlaylistName(playlistName)
        playlist?.let {
            crossRefDao.deleteByPlaylistSongCrossRefIds(it.playlistId, songId)
        }
    }

    override suspend fun getSongIdsInPlaylist(playlistName: String): List<Long> {
        val playlist = playlistDao.findByPlaylistName(playlistName)
        return playlist?.let {
            crossRefDao.getSongIdsByPlaylistId(it.playlistId)
        } ?: emptyList()
    }

    override suspend fun isSongInPlaylist(playlistName: String, songId: Long): Boolean {
        val playlist = playlistDao.findByPlaylistName(playlistName)
        return playlist?.let {
            crossRefDao.isSongInPlaylist(it.playlistId, songId)
        } ?: false
    }
}

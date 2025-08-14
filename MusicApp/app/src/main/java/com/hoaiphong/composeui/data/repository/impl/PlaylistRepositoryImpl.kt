package com.hoaiphong.composeui.data.repository.impl

import com.hoaiphong.composeui.data.local.SongLocal
import com.hoaiphong.composeui.data.local.model.entity.Playlist
import com.hoaiphong.composeui.data.local.model.entity.PlaylistSongCrossRef
import com.hoaiphong.composeui.data.local.model.entity.Song
import com.hoaiphong.composeui.data.local.model.entity.relations.PlaylistWithSongs
import com.hoaiphong.composeui.data.local.room.dao.PlayListDAO
import com.hoaiphong.composeui.data.local.room.dao.PlaylistSongCrossRefDAO
import com.hoaiphong.composeui.data.local.room.dao.SongDAO
import com.hoaiphong.composeui.data.local.toEntity
import com.hoaiphong.composeui.data.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class PlaylistRepositoryImpl(
    private val playListDAO: PlayListDAO,
    private val songDAO: SongDAO,
    private val crossRefDAO: PlaylistSongCrossRefDAO
) : PlaylistRepository {

    override suspend fun getAllPlaylists(): List<Playlist> {
        return playListDAO.getAllPlaylist()
    }

    override suspend fun getPlaylistWithSongs(playlistId: Long): PlaylistWithSongs? {
        return playListDAO.getPlaylistWithSongs(playlistId)
    }

    override suspend fun getPlaylistByName(name: String): Playlist? {
        return playListDAO.findByPlaylistName(name)
    }

    override suspend fun getPlaylistWithSongsById(id: Long): PlaylistWithSongs? {
        return playListDAO.getPlaylistWithSongsById(id)
    }

    override suspend fun addPlaylist(name: String, ownerUsername: String): Playlist? {
        val existing = playListDAO.findByPlaylistName(name)
        if (existing != null) return null

        val newPlaylist = Playlist(name = name, ownerUsername = ownerUsername)
        playListDAO.insertPlaylist(newPlaylist)

        return playListDAO.findByPlaylistName(name)
    }

    override suspend fun addSongToPlaylist(playlistId: Long, song: SongLocal) {
        val songEntity = song.toEntity()
        songDAO.insertSong(songEntity)
        crossRefDAO.addSongToPlaylist(
            PlaylistSongCrossRef(
                playlistId = playlistId,
                songId = song.id
            )
        )
    }

    override suspend fun renamePlaylist(playlistId: Long, newName: String): Boolean {
        val existing = playListDAO.findByPlaylistName(newName)
        if (existing != null) return false
        playListDAO.renamePlaylist(playlistId, newName)
        return true
    }

    override suspend fun removePlaylist(playlistId: Long) {
        playListDAO.deletePlaylistById(playlistId)
        crossRefDAO.deleteAllPlaylistSongCrossRefByPlaylistId(playlistId)
    }

    override suspend fun removeSongFromPlaylist(playlistId: Long, songId: Long) {
        crossRefDAO.deleteByPlaylistSongCrossRefIds(playlistId, songId)
    }

    override suspend fun getPlaylistsWithSongs(username: String): Flow<List<PlaylistWithSongs>> {
        return playListDAO.getPlaylistsWithSongsByUsername(username)
    }

    override suspend fun getPlaylistWithSongsByName(name: String): PlaylistWithSongs? {
        val playlist = playListDAO.findByPlaylistName(name) ?: return null
        return getPlaylistWithSongs(playlist.playlistId)
    }
    override suspend fun getSongsByIds(songIds: List<Long>): List<Song> {
        return songDAO.getSongsByIds(songIds)
    }
}

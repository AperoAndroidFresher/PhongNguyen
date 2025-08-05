package com.hoaiphong.composeui.data.model

import com.hoaiphong.composeui.db.dao.PlayListDAO
import com.hoaiphong.composeui.db.dao.PlaylistSongCrossRefDAO
import com.hoaiphong.composeui.db.dao.SongDAO
import com.hoaiphong.composeui.db.entity.Playlist
import com.hoaiphong.composeui.db.entity.PlaylistSongCrossRef
import com.hoaiphong.composeui.db.entity.relations.PlaylistWithSongs
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

    suspend fun addSongToPlaylist(playlistId: Long, song: com.hoaiphong.composeui.data.model.Song) {
        // Convert sang entity Room
        val songEntity = song.toEntity()

        // Đảm bảo bài hát đã có trong DB
        songDAO.insertSong(songEntity)

        // Thêm quan hệ playlist-song
        songDAO.addSongToPlaylist(
            PlaylistSongCrossRef(
                playlistId = playlistId,
                songId = song.id
            )
        )
    }

    suspend fun renamePlaylist(playlistId: Long, newName: String) {
        val playlist = playListDAO.getAllPlaylist().find { it.playlistId == playlistId } ?: return
        val updated = playlist.copy(name = newName)
        playListDAO.updatePlaylist(updated)
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

package com.hoaiphong.composeui.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hoaiphong.composeui.data.local.model.entity.Playlist
import com.hoaiphong.composeui.data.local.model.entity.relations.PlaylistWithSongs
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayListDAO {
    @Query(
        """
        SELECT *
        FROM playlist
    """
    )
    suspend fun getAllPlaylist(): List<Playlist>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertPlaylist(playlist: Playlist)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllPlaylist(playlists: List<Playlist>)

    @Query(
        """
        DELETE FROM playlist
        WHERE playlistId = :id
        """
    )
    suspend fun deletePlaylistById(id: Long)

    @Update
    suspend fun updatePlaylist(playlist: Playlist)

    @Query("UPDATE playlist SET playlist_name = :newName WHERE playlistId = :playlistId")
    suspend fun renamePlaylist(playlistId: Long, newName: String)

    @Transaction
    @Query(
        """
        SELECT *
        FROM playlist
        WHERE playlistId = :playlistId
        """
    )
    suspend fun getPlaylistWithSongs(playlistId: Long): PlaylistWithSongs

    @Query(
        """
        SELECT *
        FROM playlist
        WHERE playlist_name LIKE :playlistName 
        LIMIT 1
    """
    )
    suspend fun findByPlaylistName(playlistName: String): Playlist?

    @Transaction
    @Query("SELECT * FROM playlist WHERE owner_username = :username")
    fun getPlaylistsWithSongsByUsername(username: String): Flow<List<PlaylistWithSongs>>

    @Transaction
    @Query("SELECT * FROM Playlist WHERE playlistId  = :id")
    suspend fun getPlaylistWithSongsById(id: Long): PlaylistWithSongs?
}

package com.hoaiphong.composeui.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hoaiphong.composeui.data.local.model.entity.PlaylistSongCrossRef
import com.hoaiphong.composeui.data.local.model.entity.Song
import com.hoaiphong.composeui.data.local.model.entity.relations.PlaylistWithSongs

@Dao
interface SongDAO {
    @Query(
        """
        SELECT *
        FROM song
    """
    )
    suspend fun getAllSong(): List<Song>

    @Query(
        """
        DELETE FROM song
        WHERE songId = :id
    """
    )
    suspend fun deleteSongById(id: Long)

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertSong(song: Song)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllSong(vararg songs: Song)

    @Delete
    suspend fun delete(song: Song)

    @Delete
    suspend fun removeSongFromPlaylist(crossRef: PlaylistSongCrossRef)

    @Transaction
    @Query(
        """
        SELECT *
        FROM playlist
        WHERE playlistId = :playlistId
        """
    )
    suspend fun getSongsInPlaylist(playlistId: Long): PlaylistWithSongs
    @Query(
        """
        SELECT *
        FROM song
        WHERE songId IN (:songIds)
        """
    )
    suspend fun getSongsByIds(songIds: List<Long>): List<Song>
}

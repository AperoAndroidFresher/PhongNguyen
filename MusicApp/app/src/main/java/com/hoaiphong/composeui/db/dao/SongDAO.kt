package com.hoaiphong.composeui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hoaiphong.composeui.db.entity.PlaylistSongCrossRef
import com.hoaiphong.composeui.db.entity.Song
import com.hoaiphong.composeui.db.entity.relations.PlaylistWithSongs
@Dao
interface SongDAO {
    @Query("""
        SELECT *
        FROM song
    """)
    suspend  fun getAll(): List<Song>

    @Query("""
        DELETE FROM song
        WHERE songId = :id
    """)
    suspend fun deleteById(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(song: Song)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg songs: Song)

    @Delete
    suspend fun delete(song: Song)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSongToPlaylist(crossRef: PlaylistSongCrossRef)

    @Delete
    suspend fun removeSongFromPlaylist(crossRef: PlaylistSongCrossRef)

    @Transaction
    @Query("""
        SELECT *
        FROM playlist
        WHERE playlistId = :playlistId
        """
    )
    suspend fun getSongsInPlaylist(playlistId: Long): PlaylistWithSongs
}
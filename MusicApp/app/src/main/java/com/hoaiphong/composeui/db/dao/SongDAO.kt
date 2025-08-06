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
    suspend  fun getAllSong(): List<Song>

    @Query("""
        DELETE FROM song
        WHERE songId = :id
    """)
    suspend fun deleteSongById(id: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSong(song: Song)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSong(vararg songs: Song)

    @Delete
    suspend fun delete(song: Song)

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
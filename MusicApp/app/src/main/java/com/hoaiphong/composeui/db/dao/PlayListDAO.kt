package com.hoaiphong.composeui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hoaiphong.composeui.db.entity.Playlist
import com.hoaiphong.composeui.db.entity.relations.PlaylistWithSongs
@Dao
interface PlayListDAO {
    @Query("""
        SELECT *
        FROM playlist
    """)
    suspend  fun getAll(): List<Playlist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(playlist: Playlist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(playlists: List<Playlist>)

    @Query("""
        DELETE FROM playlist
        WHERE playlistId = :id
        """
    )
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(playlist: Playlist)

    @Transaction
    @Query("""
        SELECT *
        FROM playlist
        WHERE playlistId = :playlistId
        """
    )
    suspend fun getPlaylistWithSongs(playlistId: Long): PlaylistWithSongs
}
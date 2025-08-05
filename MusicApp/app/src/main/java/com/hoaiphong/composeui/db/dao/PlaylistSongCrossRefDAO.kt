package com.hoaiphong.composeui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hoaiphong.composeui.db.entity.PlaylistSongCrossRef

@Dao
interface PlaylistSongCrossRefDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(crossRef: PlaylistSongCrossRef)

    @Delete
    suspend fun delete(crossRef: PlaylistSongCrossRef)

    @Query("""
        DELETE FROM PlaylistSongCrossRef 
        WHERE playlistId = :playlistId AND songId = :songId
    """)
    suspend fun deleteByIds(playlistId: Long, songId: Long)
}
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
    suspend fun insertPlaylistSongCrossRef(crossRef: PlaylistSongCrossRef)

    @Delete
    suspend fun deletePlaylistSongCrossRef(crossRef: PlaylistSongCrossRef)

    @Query("""
        DELETE FROM PlaylistSongCrossRef 
        WHERE playlistId = :playlistId AND songId = :songId
    """)
    suspend fun deleteByPlaylistSongCrossRefIds(playlistId: Long, songId: Long)

    @Query("""
        DELETE FROM PlaylistSongCrossRef 
        WHERE playlistId = :playlistId
    """)
    suspend fun deleteAllPlaylistSongCrossRefByPlaylistId(playlistId: Long)

    @Query("""
        SELECT songId FROM PlaylistSongCrossRef 
        WHERE playlistId = :playlistId
    """)
    suspend fun getSongIdsByPlaylistId(playlistId: Long): List<Long>

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM PlaylistSongCrossRef 
            WHERE playlistId = :playlistId AND songId = :songId
        )
    """)
    suspend fun isSongInPlaylist(playlistId: Long, songId: Long): Boolean
}
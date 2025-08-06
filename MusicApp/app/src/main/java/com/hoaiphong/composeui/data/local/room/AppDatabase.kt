package com.hoaiphong.composeui.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hoaiphong.composeui.data.local.model.entity.Playlist
import com.hoaiphong.composeui.data.local.model.entity.PlaylistSongCrossRef
import com.hoaiphong.composeui.data.local.model.entity.Song
import com.hoaiphong.composeui.data.local.model.entity.User
import com.hoaiphong.composeui.data.local.room.dao.PlayListDAO
import com.hoaiphong.composeui.data.local.room.dao.PlaylistSongCrossRefDAO
import com.hoaiphong.composeui.data.local.room.dao.SongDAO
import com.hoaiphong.composeui.data.local.room.dao.UserDAO

@Database(
    entities = [User::class, Song::class, Playlist::class, PlaylistSongCrossRef::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun songDao(): SongDAO
    abstract fun playListDao(): PlayListDAO
    abstract fun playlistSongCrossRefDAO(): PlaylistSongCrossRefDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }

    }

}

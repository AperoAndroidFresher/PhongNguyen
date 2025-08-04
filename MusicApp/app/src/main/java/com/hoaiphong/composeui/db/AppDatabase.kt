package com.hoaiphong.composeui.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hoaiphong.composeui.db.dao.PlayListDAO
import com.hoaiphong.composeui.db.dao.SongDAO
import com.hoaiphong.composeui.db.dao.UserDAO
import com.hoaiphong.composeui.db.entity.Playlist
import com.hoaiphong.composeui.db.entity.PlaylistSongCrossRef
import com.hoaiphong.composeui.db.entity.Song
import com.hoaiphong.composeui.db.entity.User

@Database(    entities = [User::class, Song::class, Playlist::class, PlaylistSongCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun songDao(): SongDAO
    abstract fun playListDao(): PlayListDAO
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

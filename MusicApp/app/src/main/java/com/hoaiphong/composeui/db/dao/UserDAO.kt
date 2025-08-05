package com.hoaiphong.composeui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hoaiphong.composeui.db.entity.User

@Dao
interface UserDAO {
    @Query("""
        SELECT * 
        FROM user
        """
    )
    suspend  fun getAllUser(): List<User>

    @Query("""
        SELECT * 
        FROM user 
        WHERE user_name IN (:userName)""")
    suspend  fun loadAllByUserName(userName: String): List<User>

    @Query("""
        SELECT * 
        FROM user 
        WHERE user_name LIKE :userName 
        LIMIT 1
            """
    )
    suspend  fun findByUserName(userName: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Insert
    suspend  fun insertAllUser(vararg users: User)

    @Delete
    suspend  fun delete(user: User)

    @Query("""
        DELETE FROM user 
        WHERE user_name = :userName
        """)
    suspend fun deleteByUserName(userName: String)
}
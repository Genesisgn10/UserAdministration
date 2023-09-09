package com.example.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>
}

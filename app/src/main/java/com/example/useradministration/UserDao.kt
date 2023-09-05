package com.example.useradministration

import androidx.room.Query

interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

}
package com.example.useradministration

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
  val uid: Int,


    val name: String,


    val username: String,


    val password: String,


    val photoUrl: String,


    val address: String,


    val email: String,


    val dateOfBirth: String,


    val sex: String,


    val userType: String,


    val cpfOrCnpj: String
)

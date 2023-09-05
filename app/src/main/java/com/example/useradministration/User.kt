package com.example.useradministration

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "birthdate") val dateOfBirth: String,
    @ColumnInfo(name = "sex") val sex: String,
    @ColumnInfo(name = "type") val userType: String,
    @ColumnInfo(name = "cpf_cnpj") val cpfOrCnpj: String,
    @ColumnInfo(name = "photoUrl") val photoUrl: String,
    @ColumnInfo(name = "address") val address: String
)
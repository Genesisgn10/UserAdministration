package com.example.useradministration

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "name") @NonNull val name: String,
    @ColumnInfo(name = "username") @NonNull val username: String,
    @ColumnInfo(name = "password") @NonNull val password: String,
    @ColumnInfo(name = "email") @NonNull val email: String,
    @ColumnInfo(name = "birthdate") @NonNull val birthdate: String,
    @ColumnInfo(name = "sex") @NonNull val sex: String,
    @ColumnInfo(name = "type") @NonNull val type: String,
    @ColumnInfo(name = "cpf_cnpj") @NonNull val cpf_cnpj: String,
    @ColumnInfo(name = "photoUrl") @NonNull val photoUrl: String,
    @ColumnInfo(name = "address") @NonNull val address: String
)


data class UserModel(
    val id: Int?,
    val name: String,
    val username: String,
    val password: String,
    val email: String,
    val birthdate: String,
    val sex: String,
    val type: String,
    val cpf_cnpj: String,
    val photoUrl: String,
    val address: String
)
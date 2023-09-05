package com.example.useradministration

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
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
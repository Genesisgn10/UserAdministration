package com.example.useradministration

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

import android.os.Parcel
import android.os.Parcelable

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
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(email)
        parcel.writeString(birthdate)
        parcel.writeString(sex)
        parcel.writeString(type)
        parcel.writeString(cpf_cnpj)
        parcel.writeString(photoUrl)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}



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
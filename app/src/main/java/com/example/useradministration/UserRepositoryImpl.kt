package com.example.useradministration

import android.content.ContentValues
import android.util.Log

class UserRepositoryImpl(private val dbHelper: DatabaseHelper) : UserRepository2 {
    override fun addUser(user: User) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, user.name)
            put(DatabaseHelper.COLUMN_USERNAME, user.username)
            put(DatabaseHelper.COLUMN_PASSWORD, user.password)
            put(DatabaseHelper.COLUMN_EMAIL, user.email)
            put(DatabaseHelper.COLUMN_BIRTHDATE, user.birthdate)
            put(DatabaseHelper.COLUMN_PHOTO_URL, user.photoUrl)
            put(DatabaseHelper.COLUMN_SEX, user.sex)
            put(DatabaseHelper.COLUMN_TYPE, user.type)
            put(DatabaseHelper.COLUMN_CPF_CNPJ, user.cpf_cnpj)
            put(DatabaseHelper.COLUMN_ADDRESS, user.address)
        }

        // db.insert(DatabaseHelper.TABLE_USERS, null, values)

        try {
            val newRowId = db.insert(DatabaseHelper.TABLE_USERS, null, values)
            if (newRowId != -1L) {
                Log.d("Database", "Item inserido com sucesso. ID da linha inserida: $newRowId")
            } else {
                Log.e("Database", "Erro ao inserir o item no banco de dados.")
            }
        } catch (e: Exception) {
            Log.e("Database", "Erro ao inserir o item no banco de dados: ${e.message}")
        }
        db.close()
    }

    override fun updateUser(user: User): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, user.name)
            put(DatabaseHelper.COLUMN_USERNAME, user.username)
            put(DatabaseHelper.COLUMN_PASSWORD, user.password)
            put(DatabaseHelper.COLUMN_EMAIL, user.email)
            put(DatabaseHelper.COLUMN_BIRTHDATE, user.birthdate)
            put(DatabaseHelper.COLUMN_SEX, user.sex)
            put(DatabaseHelper.COLUMN_TYPE, user.type)
            put(DatabaseHelper.COLUMN_PHOTO_URL, user.photoUrl)
            put(DatabaseHelper.COLUMN_CPF_CNPJ, user.cpf_cnpj)
        }

        val rowsAffected = db.update(
            DatabaseHelper.TABLE_USERS,
            values,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(user.id.toString())
        )
        db.close()
        return rowsAffected
    }

    override fun deleteUser(userId: Long) {
        val db = dbHelper.writableDatabase
        db.delete(
            DatabaseHelper.TABLE_USERS,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(userId.toString())
        )
        db.close()
    }
}

interface UserRepository2 {
    fun addUser(user: User)
    fun updateUser(user: User): Int
    fun deleteUser(userId: Long)
}
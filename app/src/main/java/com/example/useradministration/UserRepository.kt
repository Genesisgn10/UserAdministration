package com.example.useradministration

import android.content.ContentValues

class UserRepository(private val dbHelper: DatabaseHelper) {

    // Método para adicionar um usuário
    fun addUser(user: User) {

    }

    // Método para atualizar um usuário
    fun updateUser(user: User): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, user.name)
            put(DatabaseHelper.COLUMN_USERNAME, user.username)
            put(DatabaseHelper.COLUMN_PASSWORD, user.password)
            put(DatabaseHelper.COLUMN_EMAIL, user.email)
            put(DatabaseHelper.COLUMN_BIRTHDATE, user.dateOfBirth)
            put(DatabaseHelper.COLUMN_SEX, user.sex)
            put(DatabaseHelper.COLUMN_TYPE, user.userType)
            put(DatabaseHelper.COLUMN_CPF_CNPJ, user.cpfOrCnpj)
        }

        val rowsAffected = db.update(
            DatabaseHelper.TABLE_USERS,
            values,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(user.uid.toString())
        )
        db.close()
        return rowsAffected
    }

    // Método para excluir um usuário
    fun deleteUser(userId: Long) {
        val db = dbHelper.writableDatabase
        db.delete(
            DatabaseHelper.TABLE_USERS,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(userId.toString())
        )
        db.close()
    }

    // Outros métodos relacionados aos usuários podem ser adicionados aqui

}

package com.example.useradministration.data

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.database.DatabaseHelper
import com.example.database.User

class UserRepositoryDatabaseImpl(
    private val dbHelper: DatabaseHelper,
    private val userDao: com.example.database.UserDao
) :
    UserRepositoryDatabase {
    override suspend fun addUser(user: User) {
        val db = dbHelper.writableDatabase
        val username = user.username
        val name = user.name
        val password = user.password
        val email = user.email
        val birthdate = user.birthdate
        val sex = user.sex
        val type = user.type
        val cpf_cnpj = user.cpf_cnpj
        val photoUrl = user.photoUrl
        val address = user.address

        // Verificar se o nome de usuário já existe na tabela
        if (usernameExists(db, username)) {
            db.close()
            throw SQLiteException("Username já existe");
        }

        // Nome de usuário não existe, insira o novo usuário
        val insertQuery =
            "INSERT INTO users (name, username, password, email, birthdate, sex, type, cpf_cnpj, photoUrl, address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

        db.execSQL(
            insertQuery,
            arrayOf(
                name,
                username,
                password,
                email,
                birthdate,
                sex,
                type,
                cpf_cnpj,
                photoUrl,
                address
            )
        )

        Log.d("Database", "Usuário inserido com sucesso.")
        db.close()
    }

    private fun usernameExists(db: SQLiteDatabase, username: String): Boolean {
        val query = "SELECT 1 FROM users WHERE username = ?"
        val cursor = db.rawQuery(query, arrayOf(username))
        return cursor.moveToFirst()
    }

    override fun updateUser(user: User): Int {
        val db = dbHelper.writableDatabase

        try {
            val args = arrayOf(
                user.name,
                user.username,
                user.password,
                user.email,
                user.birthdate,
                user.sex,
                user.type,
                user.photoUrl,
                user.cpf_cnpj,
                user.id
            )

            db.execSQL(
                "UPDATE users SET name=?, username=?, password=?, email=?, birthdate=?, sex=?, type=?, photoUrl=?, cpf_cnpj=? WHERE id=?",
                args
            )

            Log.d("Database", "Usuário atualizado com sucesso.")
            return 1
        } catch (e: Exception) {
            Log.e("Database", "Erro ao atualizar o usuário: ${e.message}")
            return 0
        } finally {
            db.close()
        }
    }

    override fun deleteUser(userId: Long) {
        val db = dbHelper.writableDatabase
        try {
            val args = arrayOf(userId)
            db.execSQL("DELETE FROM users WHERE id = ?", args)
            Log.d("Database", "Usuário excluído com sucesso.")
        } catch (e: Exception) {
            Log.e("Database", "Erro ao excluir o usuário: ${e.message}")
        } finally {
            db.close()
        }
    }

    override suspend fun getUsers(): List<User> {
        return userDao.getAllUsers()
    }
}

interface UserRepositoryDatabase {
    suspend fun addUser(user: User)
    fun updateUser(user: User): Int
    fun deleteUser(userId: Long)
    suspend fun getUsers(): List<User>
}
package com.example.useradministration

import android.util.Log

class UserRepositoryImpl(private val dbHelper: DatabaseHelper, private val userDao: UserDao) : UserRepository2 {
    override fun addUser(user: User) {
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
                user.cpf_cnpj,
                user.photoUrl,
                user.address
            )

            db.execSQL(
                "INSERT INTO users (name, username, password, email, birthdate, sex, type, cpf_cnpj, photoUrl, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                args
            )

            Log.d("Database", "Usuário inserido com sucesso.")
        } catch (e: Exception) {
            Log.e("Database", "Erro ao inserir o usuário: ${e.message}")
        } finally {
            db.close()
        }
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
                user.id // O ID do usuário que você deseja atualizar
            )

            db.execSQL(
                "UPDATE users SET name=?, username=?, password=?, email=?, birthdate=?, sex=?, type=?, photoUrl=?, cpf_cnpj=? WHERE id=?",
                args
            )

            Log.d("Database", "Usuário atualizado com sucesso.")
            return 1 // Retorna 1 para indicar sucesso (ou outro valor se preferir)
        } catch (e: Exception) {
            Log.e("Database", "Erro ao atualizar o usuário: ${e.message}")
            return 0 // Retorna 0 para indicar falha (ou outro valor se preferir)
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
   /*     val db = AppDatabase.getDatabase(requireContext())
        val userDao = db.userDao()
        val userList = userDao.getAllUsers()*/
        val userList = userDao.getAllUsers()
        return userList
    }
}

interface UserRepository2 {
    fun addUser(user: User)
    fun updateUser(user: User): Int
    fun deleteUser(userId: Long)
   suspend fun getUsers(): List<User>
}
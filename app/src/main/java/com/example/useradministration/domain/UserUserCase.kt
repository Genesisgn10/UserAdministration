package com.example.useradministration.domain

import com.example.database.User
import com.example.useradministration.data.UserRepositoryDatabase

class UserUserCase(private val userRepository: UserRepositoryDatabase) : UserUseCase {
    override suspend fun addUser(user: User) {
        userRepository.addUser(user)
    }

    override fun updateUser(user: User): Int {
        return userRepository.updateUser(user)
    }

    override fun deleteUser(userId: Long) {
        userRepository.deleteUser(userId)
    }

    override suspend fun getUsers(): List<User> {
        return userRepository.getUsers()
    }

}

interface UserUseCase {
    suspend fun addUser(user: User)
    fun updateUser(user: User): Int
    fun deleteUser(userId: Long)
    suspend fun getUsers(): List<User>
}
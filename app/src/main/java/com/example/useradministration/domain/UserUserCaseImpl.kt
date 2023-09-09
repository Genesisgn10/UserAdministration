package com.example.useradministration.domain

import com.example.database.User
import com.example.useradministration.data.UserRepositoryDatabase

class UserUserCaseImpl(private val userRepository: UserRepositoryDatabase) : UserUseCase {
    override fun addUser(user: User) {
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
    fun addUser(user: User)
    fun updateUser(user: User): Int
    fun deleteUser(userId: Long)
    suspend fun getUsers(): List<User>
}
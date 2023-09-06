package com.example.useradministration.domain

import com.example.useradministration.data.model.UserRepository
import com.example.useradministration.data.model.UserRequest
import java.lang.Exception


class PostUser (private val repository: UserRepository) : PostUserUseCase {
    override suspend fun invoke(user : UserRequest) {
        try {
            repository.postUser(user)
        } catch (e : Exception){

        }
    }
}

interface PostUserUseCase{
    suspend fun invoke(user: UserRequest)
}
package com.example.useradministration.data.model

import com.example.network.Output
import com.example.network.parseResponse
import com.example.useradministration.data.ServiceUser

class UserRepositoryImp(private val serviceUser: ServiceUser) : UserRepository {
    override suspend fun postUser(user: UserRequest) {
        val response = serviceUser.postUser(user).parseResponse()
        when (response) {
            is Output.Success -> {}
            is Output.Failure -> {}
        }
    }
}

interface UserRepository {
    suspend fun postUser(user: UserRequest)
}
package com.example.useradministration.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.useradministration.User
import com.example.useradministration.domain.PostUserUseCase
import com.example.useradministration.domain.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel(private val useCase: UserUseCase, private val postUser: PostUserUseCase) :
    ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users = _users as LiveData<List<User>>

    fun getUsers() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                useCase.getUsers()
            }
            _users.value = result
        }
    }

    fun addUser(user: User) {
        useCase.addUser(user)
    }

    fun updateUser(user: User) {
        useCase.updateUser(user)
    }

    fun deleteUser(id: Long) {
        useCase.deleteUser(id)
    }

    fun postUser(user: User) {
        viewModelScope.launch {
            //postUser.invoke(user)
        }

    }

}
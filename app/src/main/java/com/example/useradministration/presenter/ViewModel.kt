package com.example.useradministration.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.useradministration.User
import com.example.useradministration.UserRepository2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel(private val userRepository: UserRepository2) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users = _users as LiveData<List<User>>

    fun getUsers() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                // Execute a operação de banco de dados aqui
                userRepository.getUsers()
            }
            _users.value = result
        }
    }

    fun addUser(user: User){
        userRepository.addUser(user)
    }

    fun updateUser(user: User){
        userRepository.updateUser(user)
    }

}
package com.example.useradministration.presenter

import android.database.SQLException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.User
import com.example.useradministration.data.model.UserRequest
import com.example.useradministration.data.model.toUserRequest
import com.example.useradministration.domain.PostUserUseCase
import com.example.useradministration.domain.UserUseCase
import com.example.utils.State
import com.example.utils.StateError
import com.example.utils.StateLoading
import com.example.utils.StateSuccess
import com.example.utils.StateSuccessV2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Error

class UserViewModel(private val useCase: UserUseCase, private val postUser: PostUserUseCase) :
    ViewModel() {

    private val _users = MutableLiveData<State<List<User>>>()
    val users = _users as LiveData<State<List<User>>>

    fun getUsers() {
        try {
            viewModelScope.launch {
                _users.value = StateLoading(true)
                val result = withContext(Dispatchers.IO) {
                    useCase.getUsers()
                }

                _users.value = StateSuccess(result)
                _users.value = StateLoading(false)
            }
        } catch (e: Exception) {
            _users.value = StateError()
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            try {
                _users.value = StateLoading(true)
                useCase.addUser(user)
                _users.value = StateSuccessV2()
                _users.value = StateLoading(false)
            } catch (e: SQLException) {
                _users.value = StateError(Error(e.message))
                _users.value = StateLoading(false)
            } catch (e: Exception) {
                _users.value = StateError(Error(e))
                _users.value = StateLoading(false)
            }
        }
    }

    fun updateUser(user: User) {
        try {
            _users.value = StateLoading(true)
            useCase.updateUser(user)
            _users.value = StateSuccessV2()
            _users.value = StateLoading(false)
        } catch (e: Exception) {
            _users.value = StateError()
        }
    }

    fun deleteUser(id: Long) {
        useCase.deleteUser(id)
    }

    fun postUser(user: User) {
        viewModelScope.launch {
            postUser.invoke(user.toUserRequest())
        }
    }

}
package com.resqfood.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resqfood.data.pref.UserModel
import com.resqfood.data.response.LoginResponse
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {
    private val _resultLogin = MutableLiveData<LoginResponse>()
    val resultLogin: LiveData<LoginResponse> = _resultLogin

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _resultLogin.value = repository.loginUser(email, password)
        }
    }
}
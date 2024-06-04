package com.resqfood.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch
import java.io.File

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    private val _resultRegister = MutableLiveData<RegisterResponse>()
    val resultRegister: LiveData<RegisterResponse> = _resultRegister

    fun registerUser(file: File, name: String, email: String, password: String, phone: String) {
        viewModelScope.launch {
            _resultRegister.value = repository.registerUser(file, name, email, password, phone)
        }
    }
}
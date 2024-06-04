package com.resqfood.view.post_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private val _profile = MutableLiveData<UserResponse>()
    val profile: LiveData<UserResponse> = _profile

    fun getProfile() {
        viewModelScope.launch {
            repository.getSession().collect{
                _profile.value = repository.getProfile(it.token)
            }
        }
    }

}
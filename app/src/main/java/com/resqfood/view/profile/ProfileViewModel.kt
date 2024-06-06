package com.resqfood.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resqfood.data.pref.Profile
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch

// Ini belumm

class ProfileViewModel(private val repository: Repository) : ViewModel() {
//    val profile: LiveData<Profile> get() = repository.profile
//    val isLoading: LiveData<Boolean> get() = repository.isLoading
//    val error: LiveData<String> get() = repository.error
//
//    suspend fun findProfile(profileId: String) {
//        repository.findProfile(profileId)
//    }

    private val _profile = MutableLiveData<UserResponse>()
    val profile: LiveData<UserResponse> = _profile

    fun getProfile() {
        viewModelScope.launch {
            repository.getSession().collect{
                _profile.value = repository.getProfile(it.token)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}
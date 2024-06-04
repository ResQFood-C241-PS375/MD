package com.resqfood.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.resqfood.data.pref.Profile
import com.resqfood.repository.Repository

// Ini belumm

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    val profile: LiveData<Profile> get() = repository.profile
    val isLoading: LiveData<Boolean> get() = repository.isLoading
    val error: LiveData<String> get() = repository.error

    suspend fun findProfile(profileId: String) {
        repository.findProfile(profileId)
    }
}
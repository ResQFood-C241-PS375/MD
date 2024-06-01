package com.resqfood.view.posting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch
import java.io.File

class PostingViewModel(private val repository: Repository) : ViewModel() {

    private val _uploadDonation = MutableLiveData<RegisterResponse>()
    val uploadDonation: LiveData<RegisterResponse> = _uploadDonation

    private val _uploadSale = MutableLiveData<RegisterResponse>()
    val uploadSale: LiveData<RegisterResponse> = _uploadSale

    fun donationUpload(file: File,title: String, description: String, location: String) {
        viewModelScope.launch {
            repository.getSession().collect {
                _uploadDonation.value = repository.postDonation(file, title, description, location, it.token)
            }
        }
    }

    fun saleUpload(file: File,title: String, description: String, expired: String) {
        viewModelScope.launch {
            repository.getSession().collect {
                _uploadDonation.value = repository.postDonation(file, title, description, expired, it.token)
            }
        }
    }

}
package com.resqfood.view.posting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resqfood.data.response.PostDonationResponse
import com.resqfood.data.response.PostSellResponse
import com.resqfood.data.response.RegisterResponse
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch
import java.io.File

class PostingViewModel(private val repository: Repository) : ViewModel() {

    private val _uploadDonation = MutableLiveData<PostDonationResponse>()
    val uploadDonation: LiveData<PostDonationResponse> = _uploadDonation

    private val _uploadSale = MutableLiveData<PostSellResponse>()
    val uploadSale: LiveData<PostSellResponse> = _uploadSale

    fun donationUpload(file: File,title: String, description: String, location: String) {
        viewModelScope.launch {
            repository.getSession().collect {
                _uploadDonation.value = repository.postDonation(file, title, description, location, it.token, it.userId)
            }
        }
    }

    fun saleUpload(file: File,title: String, description: String, expired: String, price: String) {
        viewModelScope.launch {
            repository.getSession().collect {
                _uploadSale.value = repository.postSale(file, title, description, expired,price, it.token, it.userId)
            }
        }
    }

}
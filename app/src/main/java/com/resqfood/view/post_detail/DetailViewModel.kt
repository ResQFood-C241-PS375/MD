package com.resqfood.view.post_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resqfood.data.response.DetailDonationResponse
import com.resqfood.data.response.DetailSellResponse
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private val _donationUser = MutableLiveData<DetailDonationResponse>()
    val donationUser: LiveData<DetailDonationResponse> = _donationUser

    private val _sellUser = MutableLiveData<DetailSellResponse>()
    val sellUser: LiveData<DetailSellResponse> = _sellUser

    fun getDonationUser(id: String) {
        viewModelScope.launch {
            repository.getSession().collect{
                _donationUser.value = repository.getDonationUser(id, it.token)
            }
        }
    }

    fun getSellUser(id: String) {
        viewModelScope.launch {
            repository.getSession().collect{
                _sellUser.value = repository.getSellUser(id, it.token)
            }
        }
    }

}
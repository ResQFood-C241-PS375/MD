package com.resqfood.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.resqfood.data.pref.UserModel
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _donation = MutableLiveData<DonationResponse>()
    val donation: LiveData<DonationResponse> = _donation

    private val _sale = MutableLiveData<SaleResponse>()
    val sale: LiveData<SaleResponse> = _sale

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getDonation() {
        viewModelScope.launch {
            repository.getSession().collect{
                _donation.value = repository.getDonation(it.token)
            }
        }
    }

    fun getSale() {
        viewModelScope.launch {
            repository.getSession().collect{
                _sale.value = repository.getSale(it.token)
            }
        }
    }
}
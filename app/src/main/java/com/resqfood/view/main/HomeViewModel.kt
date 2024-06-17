package com.resqfood.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.resqfood.data.pref.UserModel
import com.resqfood.data.response.DonationResponse
import com.resqfood.data.response.SellResponse
import com.resqfood.data.response.SellSearch
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _donation = MutableLiveData<DonationResponse>()
    val donation: LiveData<DonationResponse> = _donation

    private val _sale = MutableLiveData<SellResponse>()
    val sale: LiveData<SellResponse> = _sale

    private val _search = MutableLiveData<SellSearch?>()
    val search: LiveData<SellSearch?> = _search

    fun getSearch(title: String) {
        viewModelScope.launch {
            repository.getSession().collect { user ->
                _search.value = repository.getSearchSell(title, user.token)
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

//    fun logout() {
//        viewModelScope.launch {
//            repository.logout()
//        }
//    }

    fun getDonation() {
        viewModelScope.launch {
            repository.getSession().collect{
                _donation.value = repository.getDonation(it.token)
            }
        }
    }

    fun resetSearch() {
        _search.value = null
    }

    fun getSale() {
        viewModelScope.launch {
            repository.getSession().collect{
                _sale.value = repository.getSale(it.token)
            }
        }
    }
}
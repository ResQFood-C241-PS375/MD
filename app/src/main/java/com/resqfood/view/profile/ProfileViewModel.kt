package com.resqfood.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resqfood.data.pref.DonationModel
import com.resqfood.data.pref.Profile
import com.resqfood.data.pref.SaleModel
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch

// Ini belumm

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    // tambahin variabel buat deletesale sma deletedonation
    
//    private val _profile = MutableLiveData<UserResponse>()
//    val profile: LiveData<UserResponse> = _profile
//
//    private val _donation = MutableLiveData<DonationResponse>()
//    val donation: LiveData<DonationResponse> = _donation
//
//    private val _sale = MutableLiveData<SaleResponse>()
//    val sale: LiveData<SaleResponse> = _sale
//
//    fun deleteDonation() {
//        viewModelScope.launch {
//            repository.getSession().collect{
//                // ini isi apa gitu loh cok
//            }
//        }
//    }
//
//    fun deleteSale() {
//        viewModelScope.launch {
//            repository.getSession().collect{
//                // ini isi apa gitu coks
//            }
//        }
//    }

//    fun getProfile() {
//        viewModelScope.launch {
//            repository.getSession().collect{
//                _profile.value = repository.getProfile(it.token)
//            }
//        }
//    }
//
//    fun getDonation() {
//        viewModelScope.launch {
//            repository.getSession().collect { session ->
//                val donationData = repository.getDonation(session.token)
//                _donation.value = donationData
//            }
//        }
//    }
//
//    fun getSale() {
//        viewModelScope.launch {
//            repository.getSession().collect { session ->
//                val saleData = repository.getSale(session.token)
//                _sale.value = saleData
//            }
//        }
//    }
//
//    fun logout() {
//        viewModelScope.launch {
//            repository.logout()
//        }
//    }

}
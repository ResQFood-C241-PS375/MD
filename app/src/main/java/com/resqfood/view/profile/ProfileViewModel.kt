package com.resqfood.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resqfood.data.response.DeleteDonation
import com.resqfood.data.response.UserDonation
import com.resqfood.data.response.UserInfo
import com.resqfood.data.response.UserSell
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch

// Ini belumm

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    // tambahin variabel buat deletesale sma deletedonation
    
    private val _infoUser = MutableLiveData<UserInfo>()
    val infoUser: LiveData<UserInfo> = _infoUser

    private val _donationUser = MutableLiveData<UserDonation>()
    val donationUser: LiveData<UserDonation> = _donationUser

    private val _deleteDonation = MutableLiveData<DeleteDonation>()
    val deleteDonation: LiveData<DeleteDonation> = _deleteDonation

    private val _sellUser = MutableLiveData<UserSell>()
    val sellUser: LiveData<UserSell> = _sellUser

    fun deleteDonation(id: String) {
        viewModelScope.launch {
            repository.getSession().collect{
                _deleteDonation.value = repository.deleteDonation(it.token, id)
            }
        }
    }

//    fun deleteSale() {
//        viewModelScope.launch {
//            repository.getSession().collect{
//                // ini isi apa gitu coks
//            }
//        }
//    }

    fun getUserInfo() {
        viewModelScope.launch {
            repository.getSession().collect{
                _infoUser.value = repository.getUserInfo(it.token, it.userId)
            }
        }
    }

    fun getDonationUser() {
        viewModelScope.launch {
            repository.getSession().collect {
                _donationUser.value = repository.getUserDonationInfo(it.userId, it.token)
            }
        }
    }

    fun getSellUser() {
        viewModelScope.launch {
            repository.getSession().collect {
                _sellUser.value = repository.getUserSellInfo(it.userId, it.token)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
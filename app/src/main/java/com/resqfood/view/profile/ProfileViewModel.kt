package com.resqfood.view.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resqfood.data.response.DeleteDonation
import com.resqfood.data.response.DeleteSell
import com.resqfood.data.response.UpdateUser
import com.resqfood.data.response.UserDonation
import com.resqfood.data.response.UserInfo
import com.resqfood.data.response.UserSell
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch
import java.io.File

// Ini belumm

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    // tambahin variabel buat deletesale sma deletedonation
    
    private val _infoUser = MutableLiveData<UserInfo>()
    val infoUser: LiveData<UserInfo> = _infoUser

    private val _donationUser = MutableLiveData<UserDonation>()
    val donationUser: LiveData<UserDonation> = _donationUser

    private val _sellUser = MutableLiveData<UserSell>()
    val sellUser: LiveData<UserSell> = _sellUser

    private val _deleteDonation = MutableLiveData<DeleteDonation?>()
    val deleteDonation: LiveData<DeleteDonation?> = _deleteDonation

    private val _deleteSell = MutableLiveData<DeleteSell?>()
    val deleteSell: LiveData<DeleteSell?> = _deleteSell

    private val _updateUser = MutableLiveData<UpdateUser?>()
    val updateUser: LiveData<UpdateUser?> = _updateUser

    val imageUri = MutableLiveData<Uri?>()

    fun deleteDonation(id: String) {
        viewModelScope.launch {
            repository.getSession().collect { session ->
                val result = repository.deleteDonation(id, session.token)
                if (result != null && result.message == "Donasi berhasil dihapus!") {
                    // Filter out the deleted donation from the current list
                    _donationUser.value?.let {
                        val updatedList = it.donations.filterNot { donation -> donation.donationId == id }
                        _donationUser.postValue(it.copy(donations = updatedList))
                    }
                }
                _deleteDonation.postValue(result)
            }
        }
    }

    fun deleteSell(id: String) {
        viewModelScope.launch {
            repository.getSession().collect { session ->
                val result = repository.deleteSell(id, session.token)
                if (result != null && result.message == "Penjualan berhasil dihapus!") {
                    // Filter out the deleted donation from the current list
                    _sellUser.value?.let {
                        val updatedList = it.sells.filterNot { donation -> donation.sellId == id }
                        _sellUser.postValue(it.copy(sells = updatedList))
                    }
                }
                _deleteSell.postValue(result)
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

    fun updateUser(file: File, username: String, fullName: String, email: String, password: String, phone: String) {
        viewModelScope.launch {
            repository.getSession().collect { session ->
                Log.d("infouserz","${session.userId}, ${session.token}")
                val result = repository.updateUser(session.token, session.userId, file, username, fullName, email, password, phone)
                _updateUser.postValue(result)
            }
        }
    }

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
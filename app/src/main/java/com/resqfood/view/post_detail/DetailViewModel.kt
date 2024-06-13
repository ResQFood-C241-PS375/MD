package com.resqfood.view.post_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resqfood.data.response.DetailDonationResponse
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private val _donationId = MutableLiveData<DetailDonationResponse>()
    val donationId: LiveData<DetailDonationResponse> = _donationId

//    private val _theUserId = MutableLiveData<DetailDonationResponse>()
//    val theUserId: LiveData<DetailDonationResponse> = _theUserId

    fun getDonationId(id: String) {
        viewModelScope.launch {
            repository.getSession().collect{
                _donationId.value = repository.getDonationId(id, it.token)
            }
        }
    }

//    fun getIdDonation(id: String) {
//        viewModelScope.launch {
//            repository.getSession().collect{
//                _theUserId.value = repository.getDonationId(id, it.token)
//            }
//        }
//    }
}
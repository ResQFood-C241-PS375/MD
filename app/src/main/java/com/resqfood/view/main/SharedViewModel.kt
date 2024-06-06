package com.resqfood.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resqfood.data.pref.ForSaleModel
import com.resqfood.repository.Repository
import kotlinx.coroutines.launch

class SharedViewModel(private val repository: Repository) : ViewModel() {
    private val _searchResults = MutableLiveData<List<ForSaleModel>>()
    val searchResults: LiveData<List<ForSaleModel>> get() = _searchResults

    fun searchProduct(keyword: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchResults(keyword)
                if (response.isSuccessful && response.body() != null) {
                    _searchResults.postValue(response.body()?.foods ?: emptyList())
                } else {
                    _searchResults.postValue(emptyList())
                }
            } catch (e: Exception) {
                _searchResults.postValue(emptyList())
            }
        }
    }
}
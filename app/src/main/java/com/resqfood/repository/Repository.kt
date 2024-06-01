package com.resqfood.repository

import com.resqfood.data.pref.UserModel
import com.resqfood.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val userPreference: UserPreference
) {

    suspend fun registerUser() {

    }

    suspend fun loginUser() {

    }

    suspend fun postSale() {

    }

    suspend fun postDonation() {

    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            userPreference: UserPreference
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(userPreference)
            }.also { instance = it }
    }
}
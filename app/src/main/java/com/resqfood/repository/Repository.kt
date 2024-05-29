package com.resqfood.repository

import com.resqfood.data.pref.UserPreference

class Repository private constructor(
    private val userPreference: UserPreference
) {

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
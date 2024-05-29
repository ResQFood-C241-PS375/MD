package com.resqfood.di

import android.content.Context
import com.resqfood.data.pref.UserPreference
import com.resqfood.data.pref.dataStore
import com.resqfood.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        return Repository.getInstance(pref)
    }
}
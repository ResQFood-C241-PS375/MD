package com.resqfood.repository

import com.google.gson.Gson
import com.resqfood.data.api.ApiConfig
import com.resqfood.data.api.SearchAPIService
import com.resqfood.data.pref.SaleModel
import com.resqfood.data.pref.UserModel
import com.resqfood.data.pref.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

// Ini juga nunggu CC baru disesuain

class Repository private constructor(
    private val userPreference: UserPreference,

    //misal
    private val apiService: SearchAPIService
) {

    // kayaknya nambah ini
    suspend fun getProfile(token: String): UserResponse {
        return withContext(Dispatchers.IO) {
            if (token.isEmpty()) {
                return@withContext UserResponse()
            } else {
                return@withContext ApiConfig.getApiServiceWithToken(token).getProfile().execute().body()!!
            }
        }
    }

    suspend fun getDonation(token: String): DonationResponse {
        return withContext(Dispatchers.IO) {
            if (token.isEmpty()) {
                return@withContext DonationResponse()
            } else {
                return@withContext ApiConfig.getApiServiceWithToken(token).getDonation().execute().body()!!
            }
        }
    }

    suspend fun getSale(token: String): SaleResponse {
        return withContext(Dispatchers.IO) {
            if (token.isEmpty()) {
                return@withContext SaleResponse()
            } else {
                return@withContext ApiConfig.getApiServiceWithToken(token).getSale().execute().body()!!

            }
        }
    }

    //misal gini
    suspend fun searchResults(keyword: String): Response<SaleModel> {
        return apiService.searchProduct(keyword)
    }

    suspend fun registerUser(imageFile: File, name: String, email: String, password: String, phone: String): RegisterResponse {
        return withContext(Dispatchers.IO) {
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            val response = ApiConfig.getApiService().register(multipartBody, name, email, password, phone).execute()
            if (response.isSuccessful) {
//                Log.i("info", response.message())
                return@withContext response.body()!!
            } else {
                val jsonInString = response.errorBody()?.string()
                return@withContext Gson().fromJson<RegisterResponse?>(jsonInString, RegisterResponse::class.java)
            }
        }
    }

    suspend fun loginUser(email: String, password: String): LoginResponse {
        return withContext(Dispatchers.IO) {
            val response = ApiConfig.getApiService().login(email, password).execute()
            if (response.isSuccessful) {
                return@withContext response.body()!!
            } else {
                val jsonInString = response.errorBody()?.string()
                return@withContext Gson().fromJson<LoginResponse?>(jsonInString, LoginResponse::class.java)
            }
        }
    }

    suspend fun postSale(imageFile: File, title: String, description: String, expired: String, token: String): RegisterResponse {
        return withContext(Dispatchers.IO) {
            val requestTitle = title.toRequestBody("text/plain".toMediaType())
            val requestDescription = description.toRequestBody("text/plain".toMediaType())
            val requestExpired = expired.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            val response = ApiConfig.getApiServiceWithToken(token).postSale(multipartBody,requestTitle, requestDescription, requestExpired).execute()
            return@withContext if (response.isSuccessful) {
                response.body()!!
            } else {
                val jsonInString = response.errorBody()?.string()
                Gson().fromJson<RegisterResponse?>(jsonInString, RegisterResponse::class.java)
            }
        }
    }

    suspend fun postDonation(imageFile: File, title: String, description: String, location: String, token: String): RegisterResponse {
        return withContext(Dispatchers.IO) {
            val requestTitle = title.toRequestBody("text/plain".toMediaType())
            val requestDescription = description.toRequestBody("text/plain".toMediaType())
            val requestLocation = location.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            val response = ApiConfig.getApiServiceWithToken(token).postDonation(multipartBody,requestTitle, requestDescription, requestLocation).execute()
            return@withContext if (response.isSuccessful) {
                response.body()!!
            } else {
                val jsonInString = response.errorBody()?.string()
                Gson().fromJson<RegisterResponse?>(jsonInString, RegisterResponse::class.java)
            }
        }
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
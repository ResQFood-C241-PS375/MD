package com.resqfood.repository

import android.util.Log
import com.google.gson.Gson
import com.resqfood.data.api.ApiConfig
import com.resqfood.data.pref.UserModel
import com.resqfood.data.pref.UserPreference
import com.resqfood.data.response.DeleteDonation
import com.resqfood.data.response.DeleteSell
import com.resqfood.data.response.DetailDonationResponse
import com.resqfood.data.response.DetailSellResponse
import com.resqfood.data.response.DonationResponse
import com.resqfood.data.response.LoginResponse
import com.resqfood.data.response.PostDonationResponse
import com.resqfood.data.response.PostSellResponse
import com.resqfood.data.response.RegisterResponse
import com.resqfood.data.response.SellResponse
import com.resqfood.data.response.SellSearch
import com.resqfood.data.response.UpdateUser
import com.resqfood.data.response.UserDonation
import com.resqfood.data.response.UserInfo
import com.resqfood.data.response.UserSell
import com.resqfood.data.response.UserUpdate
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
    private val userPreference: UserPreference
) {

    suspend fun getUserInfo(token: String, id: String): UserInfo {
        return withContext(Dispatchers.IO) {
            if (token.isEmpty()) {
                return@withContext UserInfo()
            } else {
                return@withContext ApiConfig.getApiServiceWithToken(token).getUserInfo(id).execute().body()!!
            }
        }
    }

    suspend fun updateUser(token: String, userId: String, imageFile: File, username: String, fullName: String, email: String, password: String, phone: String): UpdateUser? {
        return withContext(Dispatchers.IO) {
            Log.d("inforepo","repooo")
//            val requestUserId = userId.toRequestBody("text/plain".toMediaType())
            val requestUserame = username.toRequestBody("text/plain".toMediaType())
            val requestFullName = fullName.toRequestBody("text/plain".toMediaType())
            val requestEmail = email.toRequestBody("text/plain".toMediaType())
            val requestPassword = password.toRequestBody("text/plain".toMediaType())
            val requestPhone = phone.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "profile_img",
                imageFile.name,
                requestImageFile
            )
            val response = ApiConfig.getApiServiceWithToken(token).updateUserInfo(userId, requestFullName, requestUserame,  requestEmail, requestPassword, requestPhone, multipartBody).execute()
            return@withContext if (response.isSuccessful) {
                response.body()
            } else {
                val jsonInString = response.errorBody()?.string()
                Gson().fromJson<UpdateUser?>(jsonInString, UpdateUser::class.java)
            }
        }
    }


    suspend fun deleteDonation(id: String, token: String): DeleteDonation? {
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiConfig.getApiServiceWithToken(token).deleteDonation(id).execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun deleteSell(id: String, token: String): DeleteSell? {
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiConfig.getApiServiceWithToken(token).deleteSell(id).execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }


    suspend fun getUserDonationInfo(id: String, token: String): UserDonation {
        return withContext(Dispatchers.IO) {
            if (token.isEmpty()) {
                return@withContext UserDonation()
            } else {
                return@withContext ApiConfig.getApiServiceWithToken(token).getUserDonationInfo(id).execute().body()!!
            }
        }
    }

    suspend fun getUserSellInfo(id: String, token: String): UserSell {
        return withContext(Dispatchers.IO) {
            if (token.isEmpty()) {
                return@withContext UserSell()
            } else {
                return@withContext ApiConfig.getApiServiceWithToken(token).getUserSellInfo(id).execute().body()!!
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

    suspend fun getSearchSell(title: String, token: String): SellSearch {
        return withContext(Dispatchers.IO) {
            val response = ApiConfig.getApiServiceWithToken(token).getSearchSell(title).execute()
            if (response.isSuccessful) {
                response.body() ?: SellSearch()
            } else {
                SellSearch() // Return an empty SellSearch object on failure
            }
        }
    }


    suspend fun getDonationUser(id: String, token: String): DetailDonationResponse {
        return withContext(Dispatchers.IO) {
            if (token.isEmpty()) {
                return@withContext DetailDonationResponse()
            } else {
                return@withContext ApiConfig.getApiServiceWithToken(token).getDonationUser(id).execute().body()!!
            }
        }
    }

    suspend fun getSale(token: String): SellResponse {
        return withContext(Dispatchers.IO) {
            if (token.isEmpty()) {
                return@withContext SellResponse()
            } else {
                return@withContext ApiConfig.getApiServiceWithToken(token).getSale().execute().body()!!

            }
        }
    }

    suspend fun getSellUser(id: String, token: String): DetailSellResponse {
        return withContext(Dispatchers.IO) {
            if (token.isEmpty()) {
                return@withContext DetailSellResponse()
            } else {
                return@withContext ApiConfig.getApiServiceWithToken(token).getSellUser(id).execute().body()!!
            }
        }
    }


    suspend fun registerUser(imageFile: File, username: String, fullName: String ,email: String, password: String, confirmPassword: String ,phone: String): RegisterResponse {
        return withContext(Dispatchers.IO) {
            val requestUserame = username.toRequestBody("text/plain".toMediaType())
            val requestFullName = fullName.toRequestBody("text/plain".toMediaType())
            val requestEmail = email.toRequestBody("text/plain".toMediaType())
            val requestPassword = password.toRequestBody("text/plain".toMediaType())
            val requestCPassword = confirmPassword.toRequestBody("text/plain".toMediaType())
            val requestPhone = phone.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "profile_img",
                imageFile.name,
                requestImageFile
            )
            val response = ApiConfig.getApiService().register(requestUserame, requestFullName, requestEmail, requestPassword, requestCPassword ,requestPhone, multipartBody).execute()
            if (response.isSuccessful) {
                Log.i("info", response.message())
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

    suspend fun postSale(imageFile: File, title: String, description: String, expired: String, token: String, price: Int, userId: String): PostSellResponse {
        return withContext(Dispatchers.IO) {
            val requestTitle = title.toRequestBody("text/plain".toMediaType())
            val requestDescription = description.toRequestBody("text/plain".toMediaType())
            val requestExpired = expired.toRequestBody("text/plain".toMediaType())
            val requestUserId = userId.toRequestBody("text/plain".toMediaType())
            val requestPrice = price.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "sell_img",
                imageFile.name,
                requestImageFile
            )
            val response = ApiConfig.getApiServiceWithToken(token).postSale(multipartBody,requestTitle, requestDescription, requestExpired, requestPrice ,requestUserId).execute()
            return@withContext if (response.isSuccessful) {
                response.body()!!
            } else {
                val jsonInString = response.errorBody()?.string()
                Gson().fromJson<PostSellResponse?>(jsonInString, PostSellResponse::class.java)
            }
        }
    }

    suspend fun postDonation(imageFile: File, title: String, description: String, location: String, token: String, userId: String): PostDonationResponse {
        return withContext(Dispatchers.IO) {
            val requestTitle = title.toRequestBody("text/plain".toMediaType())
            val requestDescription = description.toRequestBody("text/plain".toMediaType())
            val requestLocation = location.toRequestBody("text/plain".toMediaType())
            val requestUserId = userId.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "donation_img",
                imageFile.name,
                requestImageFile
            )
            val response = ApiConfig.getApiServiceWithToken(token).postDonation(multipartBody,requestTitle, requestDescription, requestLocation, requestUserId).execute()
            return@withContext if (response.isSuccessful) {
                response.body()!!
            } else {
                val jsonInString = response.errorBody()?.string()
                Gson().fromJson<PostDonationResponse?>(jsonInString, PostDonationResponse::class.java)
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
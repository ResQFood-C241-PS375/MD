package com.resqfood.view.profile

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.resqfood.data.api.ApiConfig
import com.resqfood.databinding.ActivityProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Ini juga belomm

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    companion object {
        private const val TAG = "ProfileActivity"
        private const val PROFILE_ID = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findProfile()
    }

    private fun findProfile() {
        val client = ApiConfig.getApiService().getProfile(PROFILE_ID)
        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setProfileData(responseBody.profile)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}") // Jangan lupa dihapus atau diganti Toast ketika akan dideploy
                }
            }
            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}") // Jangan lupa dihapus atau diganti Toast ketika akan dideploy
            }
        })
    }

    private fun setProfileData(profile: ID) {
        binding.usernameCIT.text = profile.username
        binding.fullnameCIT.text = profile.fullname
        binding.emailCIT.text = profile.email
        binding.passwordCIT.text = profile.password
        binding.whatsappCIT.text = profile.whatsapp
        Glide.with(this@ProfileActivity)
            .load("https://API Body/${profile.pictureID}")// Perbaiki
            .into(binding.profileImage)
    }
}
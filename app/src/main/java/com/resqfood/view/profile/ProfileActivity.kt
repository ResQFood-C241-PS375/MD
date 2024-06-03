package com.resqfood.view.profile

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.resqfood.data.pref.Profile
import com.resqfood.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch

// Ini juga belomm

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    companion object {
        private const val TAG = "ProfileActivity"
        private const val PROFILE_ID = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        lifecycleScope.launch {
            profileViewModel.findProfile(PROFILE_ID)
        }
    }

    private fun setupObservers() {
        profileViewModel.profile.observe(this, Observer { profile ->
            setProfileData(profile)
        })

        profileViewModel.isLoading.observe(this, Observer { isLoading ->
            // Show loading indicator if needed
        })

        profileViewModel.error.observe(this, Observer { errorMessage ->
            if (errorMessage != null) {
                Log.e(TAG, "Error: $errorMessage")
                Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setProfileData(profile: Profile) {
        binding.usernameCIT.text = profile.username.toEditable()
        binding.fullnameCIT.text = profile.fullname.toEditable()
        binding.emailCIT.text = profile.email.toEditable()
        binding.passwordCIT.text = profile.password.toEditable()
        binding.whatsappCIT.text = profile.whatsapp.toEditable()
        Glide.with(this@ProfileActivity)
            .load("https://API Body/${profile.pictureID}")// Perbaiki
            .into(binding.profileImage)
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}
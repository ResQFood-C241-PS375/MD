package com.resqfood.view.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.resqfood.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Atur nilai awal untuk setiap EditText menggunakan ViewBinding
        binding.usernameCIT.setText("Alif Chicken Gowok")
        binding.fullnameCIT.setText("Ahmad Alif Putra")
        binding.emailCIT.setText("ahmadalifputra@gmail.com")
        binding.passwordCIT.setText("password123")
        binding.whatsappCIT.setText("08123456789")
    }

}
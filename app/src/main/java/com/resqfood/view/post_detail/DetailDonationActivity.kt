package com.resqfood.view.post_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.resqfood.R
import com.resqfood.databinding.ActivityDetailDonationBinding

class DetailDonationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDonationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val donationTitle = intent.getStringExtra("donation_title")
        val donationImageRes = intent.getIntExtra("donation_image", 0)

        binding.CBTextView.text = donationTitle
        binding.imageView2.setImageResource(donationImageRes)

        binding.button.setOnClickListener {
            openWhatsApp()
        }
    }

    private fun openWhatsApp() {
        val phoneNumber = "+628115907100"
        val message = "Halo, saya tertarik dengan donasi ini."
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://wa.me/$phoneNumber/?text=${Uri.encode(message)}")
        }
        startActivity(intent)
    }
}
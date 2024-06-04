package com.resqfood.view.post_detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.resqfood.R
import com.resqfood.ViewModelFactory
import com.resqfood.data.adapter.DonationAdapter
import com.resqfood.databinding.ActivityDetailDonationBinding
import com.resqfood.view.main.HomeViewModel

// Ini belum

class DetailDonationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDonationBinding
    private lateinit var detailDonation: ListDonationItem
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var userNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            detailDonation = intent.getParcelableExtra(DonationAdapter.PARCEL_NAME, ListDonationItem::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            detailDonation = intent.getParcelableExtra(DonationAdapter.PARCEL_NAME)!!
        }

        // DonationDetail
        Glide.with(binding.root)
            .load(detailDonation.image)
            .into(binding.imageDetail)
        binding.donationTitle.text = detailDonation.name
        binding.donationDescription.text = detailDonation.description
        binding.donationLocation.text = detailDonation.location

        // Users
        getProfile()

        binding.button.setOnClickListener {
            openWhatsApp()
        }
    }

    private fun getProfile() {
        viewModel.getProfile()
        viewModel.profile.observe(this) {
            binding.userName.text = it.name
            Glide.with(binding.root)
                .load(it.image)
                .into(binding.userImage)
            userNumber = it.phone
        }
    }

    private fun openWhatsApp() {
        userNumber?.let {
            val message = "Halo, saya tertarik dengan donasi ini."
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://wa.me/$it/?text=${Uri.encode(message)}")
            }
            startActivity(intent)
        } ?: run {
            Toast.makeText(this, "Nomor telepon tidak tersedia", Toast.LENGTH_SHORT).show()
        }
    }
}
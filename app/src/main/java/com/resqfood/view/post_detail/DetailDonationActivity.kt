package com.resqfood.view.post_detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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
import com.resqfood.data.response.DetailDonationResponse
import com.resqfood.data.response.Donation
import com.resqfood.data.response.Users
//import com.resqfood.data.adapter.DonationAdapter
import com.resqfood.databinding.ActivityDetailDonationBinding
import com.resqfood.view.main.HomeViewModel

// Ini belum

class DetailDonationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDonationBinding
    private lateinit var detailDonation: Donation
//    private lateinit var detailProfile: Users
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var userNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            detailDonation = intent.getParcelableExtra(EXTRA_ID, Donation::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            detailDonation = intent.getParcelableExtra(EXTRA_ID)!!
        }

        Glide.with(binding.root)
            .load(detailDonation.donationImg)
            .into(binding.imageDetail)
        binding.donationTitle.text = detailDonation.title
        binding.donationDescription.text = detailDonation.deskripsi
        binding.donationLocation.text = detailDonation.location

        Log.d("donation",detailDonation.userId)

        viewModel.getDonationUser(detailDonation.userId)
        viewModel.donationUser.observe(this){ users ->
            binding.userName.text = users.user?.namaLengkap
            Glide.with(binding.root)
                .load(users.user?.profileImg)
                .into(binding.userImage)
            userNumber = users.user?.noHp
        }

        binding.button.setOnClickListener {
            openTelephone()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun openTelephone() {
        userNumber?.let {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$it")
            }
            startActivity(intent)
        } ?: run {
            Toast.makeText(this, "Phone number is not available", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}
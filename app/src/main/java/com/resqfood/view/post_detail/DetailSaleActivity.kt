package com.resqfood.view.post_detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.resqfood.R
import com.resqfood.ViewModelFactory
import com.resqfood.data.response.Sell
//import com.resqfood.data.adapter.DonationAdapter
//import com.resqfood.data.adapter.ForSaleAdapter
import com.resqfood.databinding.ActivityDetailDonationBinding
import com.resqfood.databinding.ActivityDetailSaleBinding

// Ini belum

class DetailSaleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSaleBinding
    private lateinit var detailSale: Sell
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var userNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            detailSale = intent.getParcelableExtra(EXTRA_ID, Sell::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            detailSale= intent.getParcelableExtra(EXTRA_ID)!!
        }

        Glide.with(binding.root)
            .load(detailSale.sellImg)
            .into(binding.imageDetail)
        binding.saleTitle.text = detailSale.title
        binding.saleDescription.text = detailSale.deskripsi
        binding.saleExpired.text = detailSale.expire
        binding.sellPrice.text = "Rp ${detailSale.harga}"

        viewModel.getSellUser(detailSale.userId)
        viewModel.sellUser.observe(this) { users ->
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
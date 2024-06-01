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
import com.resqfood.databinding.ActivityDetailSaleBinding

class DetailSaleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSaleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val saleTitle = intent.getStringExtra("sale_title")
        val saleImage = intent.getIntExtra("sale_image", 0)

        binding.CBTextView.text = saleTitle
        binding.imageView2.setImageResource(saleImage)

        binding.button.setOnClickListener {
            openWhatsApp()
        }
    }

    private fun openWhatsApp() {
        val phoneNumber = "+628115907100"
        val message = "Halo, saya tertarik produk ini."
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://wa.me/$phoneNumber/?text=${Uri.encode(message)}")
        }
        startActivity(intent)
    }
}
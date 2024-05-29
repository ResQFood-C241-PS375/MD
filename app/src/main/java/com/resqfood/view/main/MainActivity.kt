package com.resqfood.view.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.resqfood.R
import com.resqfood.data.adapter.DonationAdapter
import com.resqfood.data.adapter.ForSaleAdapter
import com.resqfood.data.pref.DonationModel
import com.resqfood.data.pref.ForSaleModel
import com.resqfood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Data untuk RecyclerView Donation
        val donationList = arrayListOf(
            DonationModel("Donasi 20 Potong Ayam Goreng dan 30 Gorengan untuk Panti Asuhan", R.drawable.donation_photo),
            DonationModel("Berbagi 30 Bungkus Ayam Goreng dan untuk Driver Gojek", R.drawable.donation_photo),
            DonationModel("Donasi 20 Potong Ayam Goreng dan 30 Gorengan untuk Panti Asuhan", R.drawable.donation_photo)
        )

        val donationAdapter = DonationAdapter(donationList)
        binding.recyclerViewHorizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHorizontal.adapter = donationAdapter

        // Data untuk RecyclerView grid
        val itemList = arrayListOf(
            ForSaleModel("Roti Ban Tabur Misis dengan Selai Kacang Bergizi", R.drawable.for_sale_img),
            ForSaleModel("Kue Bantal Kampus Merdeka", R.drawable.for_sale_img),
            ForSaleModel("Gorengan Bangkit Academy", R.drawable.for_sale_img),
            ForSaleModel("Telur Bulat Isi Kentang (Beli 1 Gratis 4)", R.drawable.for_sale_img),
            ForSaleModel("Roti Ban Tabur Misis dengan Selai Kacang Bergizi", R.drawable.for_sale_img),
            ForSaleModel("Kue Bantal Kampus Merdeka", R.drawable.for_sale_img),
            ForSaleModel("Gorengan Bangkit Academy", R.drawable.for_sale_img),
            ForSaleModel("Telur Bulat Isi Kentang (Beli 1 Gratis 4)", R.drawable.for_sale_img)
        )

        val gridAdapter = ForSaleAdapter(itemList)
        binding.recyclerViewGrid.layoutManager = GridLayoutManager(this, 2) // 2 kolom dalam grid
        binding.recyclerViewGrid.adapter = gridAdapter
    }
}
package com.resqfood.view.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.resqfood.ViewModelFactory
import com.resqfood.data.adapter.DonationActionListener
//import com.resqfood.data.adapter.DonationAdapter
//import com.resqfood.data.adapter.DonationProfileAdapter
//import com.resqfood.data.adapter.ForSaleAdapter
//import com.resqfood.data.adapter.ForSaleProfileAdapter
import com.resqfood.data.adapter.SaleActionListener
import com.resqfood.data.pref.DonationModel
import com.resqfood.data.pref.Profile
import com.resqfood.data.pref.SaleModel
import com.resqfood.databinding.ActivityProfileBinding
import com.resqfood.view.main.PrimaryActivity
import com.resqfood.view.posting.PostingViewModel
import kotlinx.coroutines.launch

// Ini juga belomm

class ProfileActivity : AppCompatActivity(), DonationActionListener, SaleActionListener {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        getProfile()
//        setupRVDonationProfile()
//        setupRVSaleProfile()

//        binding.logout.setOnClickListener {
//            viewModel.logout()
//            finish()
//            val intent = Intent(this, PrimaryActivity::class.java)
//            startActivity(intent)
//        }
    }

//    private fun getProfile() {
//        viewModel.getProfile()
//        viewModel.profile.observe(this) {
//            binding.fullnameCIT.text = it.name
//            Glide.with(binding.root)
//                .load(it.image)
//                .into(binding.profileImage)
//            binding.emailCIT.text = it.email
//            binding.whatsappCIT.text = it.phone
//        }
//    }

//    private fun setupRVDonationProfile() {
//        viewModel.donation.observe(this) { donation: DonationModel ->
//            val adapter = DonationProfileAdapter()
//            adapter.submitList(donation.listDonationProfile)
//            binding.rvdonationProfile.adapter = adapter
//            binding.rvdonationProfile.layoutManager = LinearLayoutManager(this)
//        }
//        viewModel.getDonation()
//    }
//
//    private fun setupRVSaleProfile() {
//        viewModel.sale.observe(this) { sale: SaleModel ->
//            val adapter = ForSaleProfileAdapter()
//            adapter.submitList(sale.listSale)
//            binding.rvSaleProfile.adapter = adapter
//            binding.rvSaleProfile.layoutManager = LinearLayoutManager(this)
//        }
//        viewModel.getSale()
//    }

    override fun onDeleteDonation(donationId: String) {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Hapus")
            .setMessage("Apakah Anda yakin ingin menghapus profil ini?")
            .setPositiveButton("Hapus") { dialog, which ->
                // Assuming you have a method in your ViewModel to handle deletion
//                viewModel.deleteDonation(donationId)
                Toast.makeText(this, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDeleteSale(saleId: String) {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Hapus")
            .setMessage("Apakah Anda yakin ingin menghapus profil ini?")
            .setPositiveButton("Hapus") { dialog, which ->
                // Assuming you have a method in your ViewModel to handle deletion
//                viewModel.deleteSale(saleId)
                Toast.makeText(this, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }


    // bikin update profile

//    private fun setProfileData(profile: Profile) {
//        binding.usernameCIT.text = profile.username.toEditable()
//        binding.fullnameCIT.text = profile.fullname.toEditable()
//        binding.emailCIT.text = profile.email.toEditable()
//        binding.passwordCIT.text = profile.password.toEditable()
//        binding.whatsappCIT.text = profile.whatsapp.toEditable()
//        Glide.with(this@ProfileActivity)
//            .load("https://API Body/${profile.pictureID}")// Perbaiki
//            .into(binding.profileImage)
//    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}
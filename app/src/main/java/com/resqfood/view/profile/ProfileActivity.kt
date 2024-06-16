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
import com.resqfood.data.adapter.DonationProfileAdapter
import com.resqfood.data.adapter.ForSaleProfileAdapter
//import com.resqfood.data.adapter.DonationAdapter
//import com.resqfood.data.adapter.DonationProfileAdapter
//import com.resqfood.data.adapter.ForSaleAdapter
//import com.resqfood.data.adapter.ForSaleProfileAdapter
import com.resqfood.data.adapter.SaleActionListener
import com.resqfood.data.response.UserInfo
import com.resqfood.data.response.UsersItem
import com.resqfood.databinding.ActivityProfileBinding
import com.resqfood.view.main.PrimaryActivity
import com.resqfood.view.posting.PostingViewModel
import kotlinx.coroutines.launch

// Ini juga belomm

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var infoUser: UsersItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUserInfo()
        viewModel.infoUser.observe(this){ users ->
            val userInfo = users.users!![0]
            Glide.with(binding.root)
                .load(userInfo.profileImg)
                .into(binding.profileImage)
            binding.fullnameCIT.setText(userInfo.namaLengkap)
            binding.emailCIT.setText(userInfo.email)
            binding.passwordCIT.setText(userInfo.password)
            binding.whatsappCIT.setText(userInfo.noHp)
        }

        setupRVDonationProfile()
        setupRVSaleProfile()

        binding.logout.setOnClickListener {
            viewModel.logout()
            finish()
            val intent = Intent(this, PrimaryActivity::class.java)
            startActivity(intent)
        }
    }

//    private fun setupRVDonationProfile() {
//        viewModel.donationUser.observe(this) {
//            val adapter = DonationProfileAdapter(viewModel)
//            adapter.submitList(it.donations)
//            binding.rvdonationProfile.adapter = adapter
//            binding.rvdonationProfile.layoutManager = LinearLayoutManager(this)
//        }
//        viewModel.getDonationUser()
//
//        viewModel.deleteDonation.observe(this) {delete ->
//            delete?.let {
//                if (it.message == "Donasi berhasil dihapus!") {
//                    Toast.makeText(this, "Donation deleted successfully", Toast.LENGTH_SHORT).show()
//                }  else {
//                    Toast.makeText(this, "Failed to delete donation", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }


    private fun setupRVDonationProfile() {
        viewModel.donationUser.observe(this) {
            val adapter = DonationProfileAdapter(viewModel)
            adapter.submitList(it.donations)
            adapter.setOnItemClickCallback(object : DonationProfileAdapter.OnItemClickCallback {
                override fun onItemClicked(id: String) {
                    AlertDialog.Builder(this@ProfileActivity)
                        .setTitle("Konfirmasi Hapus")
                        .setMessage("Apakah Anda yakin ingin menghapus donasi ini?")
                        .setPositiveButton("Hapus") { _, _ ->
                            viewModel.deleteDonation(id)
                            Log.d("info3",id)
                            viewModel.deleteDonation.observe(this@ProfileActivity) { delete ->
                                delete?.let {
                                    if (it.message == "Donasi berhasil dihapus!") {
                                        Toast.makeText(
                                            this@ProfileActivity,
                                            "Donation deleted successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            this@ProfileActivity,
                                            "Failed to delete donation: ${it.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
//                            Toast.makeText(this@ProfileActivity, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("Batal", null)
                        .show()
                }
            })
            binding.rvdonationProfile.adapter = adapter
            binding.rvdonationProfile.layoutManager = LinearLayoutManager(this)
        }
        viewModel.getDonationUser()
    }
//        adapter.setOnItemClickCallback(object : DonationProfileAdapter.OnItemClickCallback {
//            override fun onItemClicked(id: String) {
//                AlertDialog.Builder(this@ProfileActivity)
//                    .setTitle("Konfirmasi Hapus")
//                    .setMessage("Apakah Anda yakin ingin menghapus donasi ini?")
//                    .setPositiveButton("Hapus") { _, _ ->
//                        viewModel.deleteDonation(id)
//                        Toast.makeText(this@ProfileActivity, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
//                    }
//                    .setNegativeButton("Batal", null)
//                    .show()
//            }
//        })
//    }


    private fun setupRVSaleProfile() {
        viewModel.sellUser.observe(this) {
            val adapter = ForSaleProfileAdapter()
            adapter.submitList(it.sells)
            binding.rvSaleProfile.adapter = adapter
            binding.rvSaleProfile.layoutManager = LinearLayoutManager(this)
        }
        viewModel.getSellUser()
    }

//    override fun onDeleteDonation(donationId: String) {

//    }
//
//    override fun onDeleteSale(saleId: String) {
//        AlertDialog.Builder(this)
//            .setTitle("Konfirmasi Hapus")
//            .setMessage("Apakah Anda yakin ingin menghapus profil ini?")
//            .setPositiveButton("Hapus") { dialog, which ->
//                // Assuming you have a method in your ViewModel to handle deletion
////                viewModel.deleteSale(saleId)
//                Toast.makeText(this, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
//            }
//            .setNegativeButton("Batal", null)
//            .show()
//    }


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

//    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}
package com.resqfood.view.profile

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import com.resqfood.data.response.UserDonation
import com.resqfood.data.response.UserInfo
import com.resqfood.data.response.UsersItem
import com.resqfood.databinding.ActivityProfileBinding
import com.resqfood.reduceFileImage
import com.resqfood.uriToFile
import com.resqfood.view.login.LoginActivity
import com.resqfood.view.main.PrimaryActivity
import com.resqfood.view.posting.PostingViewModel
import kotlinx.coroutines.launch

// Ini juga belomm

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupUI()

        binding.profileImage.setOnClickListener { startGallery() }

        binding.btnSubmitEdit.setOnClickListener {
            showLoading(true)

            // bikin if kalo currentImageUri nya kosong

            currentImageUri?.let {
                viewModel.updateUser(
                    uriToFile(it, this).reduceFileImage(),
                    binding.fullnameCIT.text.toString(),
                    binding.usernameCIT.text.toString(),
                    binding.emailCIT.text.toString(),
                    binding.passwordCIT.text.toString(),
                    binding.whatsappCIT.text.toString()
                )
            }
        }
    }

    private fun setupObservers() {
        viewModel.infoUser.observe(this) { users ->
            val userInfo = users.users!![0]
            Glide.with(binding.root)
                .load(userInfo.profileImg)
                .into(binding.profileImage)
            binding.fullnameCIT.setText(userInfo.namaLengkap)
            binding.emailCIT.setText(userInfo.email)
            binding.usernameCIT.setText(userInfo.username)
            binding.whatsappCIT.setText(userInfo.noHp)
        }

        viewModel.updateUser.observe(this) { updateUser ->
            showLoading(false)
            updateUser?.let {
                val alertDialog = if (it.error == true) {
                    AlertDialog.Builder(this).apply {
                        setTitle("Kesalahan Pemasukan Data !")
                        setMessage(it.message)
                        setNegativeButton("isi ulang kembali") { dialog, _ ->
                            dialog.cancel()
                        }
                        create()
                    }
                } else {
                    AlertDialog.Builder(this).apply {
                        setTitle("Data berhasil diubah !")
                        setMessage(it.message)
                        setNegativeButton("tutup") { dialog, _ ->
                            dialog.dismiss()
                            dialog.cancel()
                            finish()
                        }
                        create()
                    }
                }
                alertDialog.show()
            }
        }

        viewModel.donationUser.observe(this) { userDonation ->
            userDonation?.let {
                updateDonationList(it)
            }
        }

        viewModel.deleteDonation.observe(this) { deleteResult ->
            deleteResult?.let {
                if (it.message == "Donasi berhasil dihapus!") {
                    Toast.makeText(this, "Donation deleted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to delete donation: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.logout.setOnClickListener {
            viewModel.logout()
            finish()
            val intent = Intent(this, PrimaryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupUI() {
        showLoading(false)
        viewModel.getUserInfo()
        setupRVDonationProfile()
        setupRVSellProfile()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.profileImage.setImageURI(it)
        }
    }

    private fun updateDonationList(userDonation: UserDonation) {
        val adapter = DonationProfileAdapter(viewModel)
        adapter.submitList(userDonation.donations)
        adapter.setOnItemClickCallback(object : DonationProfileAdapter.OnItemClickCallback {
            override fun onItemClicked(id: String) {
                AlertDialog.Builder(this@ProfileActivity)
                    .setTitle("Konfirmasi Hapus")
                    .setMessage("Apakah Anda yakin ingin menghapus donasi ini?")
                    .setPositiveButton("Hapus") { _, _ ->
                        viewModel.deleteDonation(id)
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        })
        binding.rvdonationProfile.adapter = adapter
        binding.rvdonationProfile.layoutManager = LinearLayoutManager(this)
    }

    private fun setupRVDonationProfile() {
        binding.rvdonationProfile.layoutManager = LinearLayoutManager(this)
    }

    private fun setupRVSellProfile() {
        binding.rvSaleProfile.layoutManager = LinearLayoutManager(this)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingProfile.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

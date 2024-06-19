package com.resqfood.view.profile.ui.user_profile

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.resqfood.R
import com.resqfood.ViewModelFactory
import com.resqfood.databinding.FragmentHomeBinding
import com.resqfood.databinding.FragmentUserBinding
import com.resqfood.reduceFileImage
import com.resqfood.uriToFile
import com.resqfood.view.main.HomeViewModel
import com.resqfood.view.main.PrimaryActivity
import com.resqfood.view.profile.ProfileViewModel

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null

    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupUI()

        binding.profileImage.setOnClickListener { startGallery() }

        binding.btnSubmitEdit.setOnClickListener {
            showLoading(true)

            if (currentImageUri == null
                || binding.fullnameCIT.text.toString().isEmpty()
                || binding.usernameCIT.text.toString().isEmpty()
                || binding.emailCIT.text.toString().isEmpty()
                || binding.passwordCIT.text.toString().isEmpty()
                || binding.whatsappCIT.text.toString().isEmpty()) {

                AlertDialog.Builder(requireActivity()).apply {
                    setTitle("Empty data")
                    setMessage("Please fill all the fields and select an image!")
                    setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }
                    show()
                }
                showLoading(false)
            } else {
                currentImageUri?.let {
                    viewModel.updateUser(
                        uriToFile(it, requireActivity()).reduceFileImage(),
                        binding.fullnameCIT.text.toString(),
                        binding.usernameCIT.text.toString(),
                        binding.emailCIT.text.toString(),
                        binding.passwordCIT.text.toString(),
                        binding.whatsappCIT.text.toString()
                    )
                }
            }
        }
    }

    private fun setupObservers() {
        viewModel.infoUser.observe(viewLifecycleOwner) { users ->
            val userInfo = users.users!![0]
            Glide.with(binding.root)
                .load(userInfo.profileImg)
                .into(binding.oldProfileImage)
            binding.fullnameCIT.setText(userInfo.namaLengkap)
            binding.emailCIT.setText(userInfo.email)
            binding.usernameCIT.setText(userInfo.username)
            binding.whatsappCIT.setText(userInfo.noHp)
        }

        viewModel.updateUser.observe(viewLifecycleOwner) { updateUser ->
            showLoading(false)
            updateUser?.let {
                val alertDialog = if (it.error == true) {
                    AlertDialog.Builder(requireActivity()).apply {
                        setTitle("Wrong Input")
                        setMessage("Please input correctly")
                        setNegativeButton("OK") { dialog, _ ->
                            dialog.cancel()
                        }
                        create()
                    }
                } else {
                    AlertDialog.Builder(requireActivity()).apply {
                        setTitle("Data successfully changed !")
                        setMessage(it.message)
                        setNegativeButton("Close") { dialog, _ ->
                            dialog.dismiss()
                            dialog.cancel()
                            val intent = Intent(requireActivity(), PrimaryActivity::class.java)
                            startActivity(intent)
                        }
                        create()
                    }
                }
                alertDialog.show()
            }
        }

        binding.logout.setOnClickListener {
            viewModel.logout()
//            finish()
            val intent = Intent(requireActivity(), PrimaryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupUI() {
        showLoading(false)
        viewModel.getUserInfo()
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

    private fun showLoading(isLoading: Boolean) {
        binding.loadingProfile.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
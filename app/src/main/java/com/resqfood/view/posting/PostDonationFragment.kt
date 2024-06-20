package com.resqfood.view.posting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.resqfood.R
import com.resqfood.ViewModelFactory
import com.resqfood.data.response.PostDonationResponse
import com.resqfood.databinding.FragmentPostDonationBinding
import com.resqfood.reduceFileImage
import com.resqfood.uriToFile
import com.resqfood.view.main.PrimaryActivity

class PostDonationFragment : Fragment() {

    private var _binding: FragmentPostDonationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PostingViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDonationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(false)

        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            currentImageUri = uri
            showImage()
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener {
//            postDonation()
              setupObservers()
        }
    }


    private fun setupObservers() {
        postDonation()
        viewModel.uploadDonation.observe(viewLifecycleOwner) { result ->
            handleDonationResult(result)
        }
    }

    private fun handleDonationResult(result: PostDonationResponse) {
        showLoading(false)
        if (result.message != "Donasi berhasil dibuat!") {
            showErrorDialog()
        } else {
            showSuccessDialog()
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Wrong Input !")
            setMessage("Please check your inputs or network and try again!")
            setNegativeButton("Retry") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Success!")
            setMessage("Your donation info has been posted")
            setPositiveButton("OK") { dialog, _ ->
                val intent = Intent(requireActivity(), PrimaryActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }
            show()
        }
    }

    private fun postDonation() {
        showLoading(true)
        // Cek jika semua fields sudah terisi
        if (currentImageUri == null ||
            binding.inputDonation.text.toString().isEmpty() ||
            binding.inputDescription.text.toString().isEmpty() ||
            binding.inputLocation.text.toString().isEmpty()) {
            showLoading(false)
            showErrorDialog()
        } else {
            // Posting donation...
            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, requireActivity()).reduceFileImage()
                val title = binding.inputDonation.text.toString()
                val description = binding.inputDescription.text.toString()
                val location = binding.inputLocation.text.toString()

                viewModel.donationUpload(imageFile, title, description, location)
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            viewModel.imageUri.value = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.galleryButton.setImageURI(it)
        }
    }

//    private fun postDonation() {
//
//        // dibikin mirip kaya donation aja deh logic klo ga keisi
//
//        // perbaikan dikit lagi buggnyaa
//
//        // gausa dibuat variabel
//
//        showLoading(true)
//
//        if (currentImageUri == null ||
//            binding.inputDonation.text.toString().isEmpty() ||
//            binding.inputDescription.text.toString().isEmpty() ||
//            binding.inputLocation.text.toString().isEmpty()) {
//            AlertDialog.Builder(requireActivity()).apply {
//                setTitle("Empty data")
//                setMessage("Please fill all the fields and select an image!")
//                setPositiveButton("OK") { dialog, which ->
//                    dialog.dismiss()
//                }
//                show()
//            }
//            showLoading(false)
//        } else {
//            currentImageUri?.let { uri ->
//
//                val imageFile = uriToFile(uri, requireActivity()).reduceFileImage()
//                val title = binding.inputDonation.text.toString()
//                val description = binding.inputDescription.text.toString()
//                val location = binding.inputLocation.text.toString()
//
//                viewModel.donationUpload(imageFile, title, description, location)
//                viewModel.uploadDonation.observe(viewLifecycleOwner) { result: PostDonationResponse ->
//                    var alertDialog: AlertDialog.Builder? = null
//                    if (result.message != "Donasi berhasil dibuat!") {
//                        showLoading(false)
//                        alertDialog = AlertDialog.Builder(requireActivity()).apply {
//                            setTitle("Wrong Input !")
//                            setMessage(R.string.error_input)
//                            setNegativeButton("Upload Again") { dialog, _ ->
//                                dialog.cancel()
//                                dialog.dismiss()
//                            }
//                            create()
//                        }
//                        alertDialog.show()
//
//                        showLoading(false)
//                        alertDialog = AlertDialog.Builder(requireActivity()).apply {
//
//                            val appInfoArray = resources.getStringArray(R.array.app_info)
//                            val appInfoString = appInfoArray.joinToString("\n \n")
//
//                            setTitle("Succes... Disclaimer !")
//                            setMessage(appInfoString)
//                            setNegativeButton("Next") { dialog, _ ->
//                                val intent = Intent(requireActivity(), PrimaryActivity::class.java)
//                                startActivity(intent)
//                                dialog.cancel()
//                                dialog.dismiss()
//                            }
//                            create()
//                        }
//                        alertDialog.show()
//                    }
//                }
//            }
//        }
//
////        val title = binding.inputDonation.text.toString()
////        val description = binding.inputDescription.text.toString()
////        val location = binding.inputLocation.text.toString()
////
////        if (title.isEmpty() || currentImageUri == null || description.isEmpty() || location.isEmpty()) {
////            AlertDialog.Builder(requireContext()).apply {
////                setTitle("Empty data")
////                setMessage("Please fill all the fields and select an image!")
////                setPositiveButton("OK") { dialog, which ->
////                    dialog.dismiss()
////                }
////                show()
////            }
////            showLoading(false)
////        } else {
////            currentImageUri?.let { uri ->
////                val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
////                viewModel.donationUpload(imageFile, title, description, location)
////                viewModel.uploadDonation.observe(viewLifecycleOwner) { result: PostDonationResponse ->
////                    var alertDialog: AlertDialog.Builder? = null
////                    if (result.message != "Donasi berhasil dibuat!") {
////                        showLoading(false)
////                        alertDialog = AlertDialog.Builder(requireActivity()).apply {
////                            setTitle("Wrong Input !")
////                            setMessage(R.string.error_input)
////                            setNegativeButton("Upload Again") { dialog, _ ->
////                                dialog.cancel()
////                                dialog.dismiss()
////                            }
////                            create()
////                        }
////                        alertDialog.show()
////
////                        showLoading(false)
////                        alertDialog = AlertDialog.Builder(requireActivity()).apply {
////
////                            val appInfoArray = resources.getStringArray(R.array.app_info)
////                            val appInfoString = appInfoArray.joinToString("\n \n")
////
////                            setTitle("Succes... Disclaimer !")
////                            setMessage(appInfoString)
////                            setNegativeButton("Next") { dialog, _ ->
////                                val intent = Intent(requireActivity(), PrimaryActivity::class.java)
////                                startActivity(intent)
////                                dialog.cancel()
////                                dialog.dismiss()
////                            }
////                            create()
////                        }
////                        alertDialog.show()
////                    }
////                }
////            }
////        }
//    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingPost.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
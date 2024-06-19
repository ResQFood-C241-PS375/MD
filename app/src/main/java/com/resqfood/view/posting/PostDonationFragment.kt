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

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener {
            postDonation()
            showLoading(true)
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

    private fun postDonation() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireActivity()).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val title = binding.inputDonation.text.toString()
            val description = binding.inputDescription.text.toString()
            val location = binding.inputLocation.text.toString()

//            if (title.isEmpty()), kaya gini di lanjutin

            viewModel.donationUpload(imageFile,title, description, location)
            viewModel.uploadDonation.observe(viewLifecycleOwner) { result: PostDonationResponse ->
                var alertDialog: AlertDialog.Builder? = null
                if (result.message != "Donasi berhasil dibuat!") {
                    showLoading(false)
                    alertDialog = AlertDialog.Builder(requireActivity()).apply {
                        setTitle("Wrong Input !")
                        setMessage(R.string.error_input)
                        setNegativeButton("Upload Again") { dialog, _ ->
                            dialog.cancel()
                            dialog.dismiss()
                        }
                        create()
                    }
                    alertDialog.show()
                } else {
                    showLoading(false)
                    alertDialog = AlertDialog.Builder(requireActivity()).apply {

                        val appInfoArray = resources.getStringArray(R.array.app_info)
                        val appInfoString = appInfoArray.joinToString("\n \n")

                        setTitle("Succes... Disclaimer !")
                        setMessage(appInfoString)
                        setNegativeButton("Next") { dialog, _ ->
                            val intent = Intent(requireActivity(), PrimaryActivity::class.java)
                            startActivity(intent)
                            dialog.cancel()
                            dialog.dismiss()
                        }
                        create()
                    }
                    alertDialog.show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingPost.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
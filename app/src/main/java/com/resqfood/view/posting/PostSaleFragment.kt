package com.resqfood.view.posting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.resqfood.R
import com.resqfood.ViewModelFactory
import com.resqfood.data.response.PostSellResponse
import com.resqfood.databinding.FragmentPostSaleBinding
import com.resqfood.helper.ImageClassifierHelper
import com.resqfood.reduceFileImage
import com.resqfood.uriToFile
import com.resqfood.view.main.PrimaryActivity
import org.tensorflow.lite.task.vision.classifier.Classifications

class PostSaleFragment : Fragment() {

    private var _binding: FragmentPostSaleBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private val viewModel by viewModels<PostingViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private var labelClassifications: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostSaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(false)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener {
            setupObservers()
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
            analyzeImage(uri)
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

    private fun analyzeImage(uri: Uri) {
        imageClassifierHelper = ImageClassifierHelper(
            context = requireActivity(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    requireActivity().runOnUiThread {
                        results?.let { it ->
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                val highestConfidenceCategory = it[0].categories.maxByOrNull { it.score }
                                val predictedLabel = if (highestConfidenceCategory != null && highestConfidenceCategory.score > 0.8) {
                                    getString(R.string.label_0)
                                    labelClassifications = getString(R.string.label_0)
                                } else {
                                    getString(R.string.label_1)
                                }
                                if (predictedLabel == "Bukan Roti") {
                                    showAlert("This is not bread thing !")
                                }
                            }
                        }
                    }
                }
            }
        )
        imageClassifierHelper.classifyStaticImage(uri)
    }


    private fun setupObservers() {
        postSale()
        viewModel.uploadSale.observe(viewLifecycleOwner) { result ->
            handleSaleResult(result)
        }
    }

    private fun handleSaleResult(result: PostSellResponse) {
        showLoading(false)
        if (result.error == true) {
            showErrorDialog()
        } else {
            showSuccessDialog()
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Wrong Input")
            setMessage("Data input didn\'t matched correctly or it's not a bread photo")
            setNegativeButton("Upload Again") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Success!")
            setMessage("Your food sale has been posted")
            setNegativeButton("OK") { dialog, _ ->
                val intent = Intent(requireActivity(), PrimaryActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }
            show()
        }
    }

    private fun postSale() {
        showLoading(true)
            if (currentImageUri == null || labelClassifications != "Roti" ||
                binding.inputDishes.text.toString().isEmpty() ||
                binding.inputDescription.text.toString().isEmpty() ||
                binding.inputExpired.text.toString().isEmpty() ||
                binding.inputPrice.text.toString().isEmpty()) {
                showLoading(false)
                showErrorDialog()
            } else {
                currentImageUri?.let { uri ->
                    val imageFile = uriToFile(uri, requireActivity()).reduceFileImage()
                    val title = binding.inputDishes.text.toString()
                    val description = binding.inputDescription.text.toString()
                    val expired = binding.inputExpired.text.toString()
                    val price = binding.inputPrice.text.toString()
                    val priceInt = price.toInt()

                    viewModel.saleUpload(imageFile,title, description, expired, priceInt)
                }
            }
    }

    private fun showAlert(message: String) {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.alert_title))
            setIcon(R.drawable.ic_warning)
            setMessage(message)
            setPositiveButton("Upload Again") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingPost.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
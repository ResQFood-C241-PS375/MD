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
            postSale()
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

    private fun postSale() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireActivity()).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val title = binding.inputDishes.text.toString()
            val description = binding.inputDescription.text.toString()
            val expired = binding.inputExpired.text.toString()
            val price = binding.inputPrice.text.toString()
            val priceInt = price.toInt()

            //            if (title.isEmpty()), kaya gini di lanjutin

            viewModel.saleUpload(imageFile,title, description, expired, priceInt)
            viewModel.uploadSale.observe(viewLifecycleOwner) { result: PostSellResponse ->
                var alertDialog: AlertDialog.Builder? = null
                if (result.error == true) {
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
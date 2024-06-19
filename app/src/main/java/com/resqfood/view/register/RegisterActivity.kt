package com.resqfood.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.resqfood.R
import com.resqfood.ViewModelFactory
import com.resqfood.databinding.ActivityRegisterBinding
import com.resqfood.reduceFileImage
import com.resqfood.uriToFile
import com.resqfood.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)
        setupView()
        setupAction()
        playAnimation()

        binding.imageEdit.setOnClickListener { startGallery() }

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
            binding.imageEdit.setImageURI(it)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        viewModel.resultRegister.observe(this) {
            showLoading(true)
            val alertDialog: AlertDialog.Builder?
            if (it.error == true) {
                showLoading(false)
                alertDialog = AlertDialog.Builder(this).apply {
                    setTitle("Wrong Input Data !")
                    setMessage("Data didn't match")
                    setNegativeButton("Input Again") { dialog, _ ->
                        dialog.cancel()
                    }
                    create()
                }
                alertDialog.show()
            } else {
                showLoading(false)
                alertDialog = AlertDialog.Builder(this).apply {
                    setTitle("Account Successfully Created")
                    setMessage("Please Login")
                    setNegativeButton("Login") { dialog, _ ->
                        dialog.dismiss()
                        dialog.cancel()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                }
                alertDialog.show()
            }
        }
        binding.signupButton.setOnClickListener {
            showLoading(true)

            if (currentImageUri == null
                || binding.userNameEditText.text.toString().isEmpty()
                || binding.fullNameEditText.text.toString().isEmpty()
                || binding.emailEditText.text.toString().isEmpty()
                || binding.passwordEditText.text.toString().isEmpty()
                || binding.confirmPasswordEditText.text.toString().isEmpty()
                || binding.phoneEditText.text.toString().isEmpty()
                ) {

                android.app.AlertDialog.Builder(this).apply {
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
                    viewModel.registerUser(
                        uriToFile(it, this).reduceFileImage(),
                    binding.userNameEditText.text.toString(),
                    binding.fullNameEditText.text.toString(),
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString(),
                    binding.confirmPasswordEditText.text.toString(),
                    binding.phoneEditText.text.toString()
                    )
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.userNameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.userNameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val fnameTextView =
            ObjectAnimator.ofFloat(binding.fullNameTextView, View.ALPHA, 1f).setDuration(100)
        val fnameEditTextLayout =
            ObjectAnimator.ofFloat(binding.fullNameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val cPasswordTextView =
            ObjectAnimator.ofFloat(binding.confirmPasswordTextView, View.ALPHA, 1f).setDuration(100)
        val cPasswordEditTextLayout =
            ObjectAnimator.ofFloat(binding.confirmPasswordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val phoneTextView =
            ObjectAnimator.ofFloat(binding.phoneTextView, View.ALPHA, 1f).setDuration(100)
        val phoneEditTextLayout =
            ObjectAnimator.ofFloat(binding.phoneEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                fnameTextView,
                fnameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                cPasswordTextView,
                cPasswordEditTextLayout,
                phoneTextView,
                phoneEditTextLayout,
                signup
            )
            startDelay = 100
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingRegis.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
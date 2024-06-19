package com.resqfood.view.profile.ui.user_donation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.resqfood.ViewModelFactory
import com.resqfood.data.adapter.DonationProfileAdapter
import com.resqfood.data.response.UserDonation
import com.resqfood.databinding.FragmentUserDonationBinding
import com.resqfood.view.profile.ProfileViewModel

class UserDonationFragment : Fragment() {

    private var _binding: FragmentUserDonationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserDonationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupUI()

    }

    private fun setupObservers(){
        viewModel.getDonationUser()
        viewModel.donationUser.observe(viewLifecycleOwner) { userDonation ->
            userDonation?.let {
                updateDonationList(it)
            }
        }
        viewModel.deleteDonation.observe(viewLifecycleOwner) { deleteResult ->
            deleteResult?.let {
                if (it.message == "Donasi berhasil dihapus!") {
                    Toast.makeText(requireActivity(), "Donation deleted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), "Failed to delete donation: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupUI(){
        setupRVDonationProfile()
    }

    private fun updateDonationList(userDonation: UserDonation) {
        val adapter = DonationProfileAdapter(viewModel)
        adapter.submitList(userDonation.donations)
        adapter.setOnItemClickCallback(object : DonationProfileAdapter.OnItemClickCallback {
            override fun onItemClicked(id: String) {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Delete Confirmation")
                    .setMessage("Are you sure you want to delete this donation?")
                    .setPositiveButton("Delete") { _, _ ->
                        viewModel.deleteDonation(id)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        })
        binding.rvdonationProfile.adapter = adapter
        binding.rvdonationProfile.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun setupRVDonationProfile() {
        binding.rvdonationProfile.layoutManager = LinearLayoutManager(requireActivity())
    }
}
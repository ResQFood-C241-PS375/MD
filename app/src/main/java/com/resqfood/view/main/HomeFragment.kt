package com.resqfood.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.resqfood.ViewModelFactory
import com.resqfood.data.adapter.DonationAdapter
import com.resqfood.data.adapter.ForSaleAdapter
import com.resqfood.data.api.ApiConfig
import com.resqfood.data.pref.DonationModel
import com.resqfood.data.pref.ForSaleModel
import com.resqfood.databinding.FragmentHomeBinding
import com.resqfood.view.post_detail.DetailDonationActivity
import com.resqfood.view.post_detail.DetailSaleActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ini belum

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRVDonation()
        setupRVSale()

    }



    private fun setupRVSale() {
        viewModel.getDonation()
        viewModel.donation.observe(viewLifecycleOwner) { donation: DonationResponse ->
            val adapter = DonationAdapter()
            adapter.submitList(donation.listDonation)
            binding.rvDonation.adapter = adapter
            binding.rvDonation.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupRVDonation() {
        viewModel.getSale()
        viewModel.sale.observe(viewLifecycleOwner) { sale: SaleResponse ->
            val adapter = ForSaleAdapter()
            adapter.submitList(sale.listSale)
            binding.rvSale.adapter = adapter
            binding.rvSale.layoutManager = GridLayoutManager(requireActivity(), 2) // 2 kolom dalam grid
        }
    }

    override fun onResume() {
        super.onResume()
        setupRVDonation()
        setupRVSale()
    }
}
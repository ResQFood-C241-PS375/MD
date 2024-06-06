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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.resqfood.ViewModelFactory
import com.resqfood.data.adapter.DonationAdapter
import com.resqfood.data.adapter.ForSaleAdapter
import com.resqfood.data.api.ApiConfig
import com.resqfood.data.pref.DonationModel
import com.resqfood.data.pref.ForSaleModel
import com.resqfood.data.pref.SaleModel
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

    private val sharedViewModel: SharedViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var forSaleAdapter: ForSaleAdapter

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

        //misal
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRVDonation() {
        viewModel.getDonation()
        viewModel.donation.observe(viewLifecycleOwner) { donation: DonationModel ->
            val adapter = DonationAdapter()
            adapter.submitList(donation.listDonation)
            binding.rvDonation.adapter = adapter
            binding.rvDonation.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupRVSale() {
        viewModel.getSale()
        viewModel.sale.observe(viewLifecycleOwner) { sale: SaleModel ->
            val adapter = ForSaleAdapter()
            adapter.submitList(sale.sale)
            binding.rvSale.adapter = adapter
            binding.rvSale.layoutManager = GridLayoutManager(requireActivity(), 2) // 2 kolom dalam grid
        }
    }

    private fun setupRecyclerView() {
        forSaleAdapter = ForSaleAdapter()
        binding.rvSale.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSale.adapter = forSaleAdapter
    }

    private fun observeViewModel() {
        sharedViewModel.searchResults.observe(viewLifecycleOwner, Observer { sale: List<ForSaleModel> ->
            forSaleAdapter.submitList(sale)
        })
    }

    override fun onResume() {
        super.onResume()
        setupRVDonation()
        setupRVSale()
        setupRecyclerView()
        observeViewModel()
    }
}
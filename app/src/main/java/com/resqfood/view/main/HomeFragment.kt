package com.resqfood.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.resqfood.R
import com.resqfood.ViewModelFactory
import com.resqfood.data.adapter.DonationAdapter
import com.resqfood.data.adapter.ForSaleAdapter
import com.resqfood.data.pref.DonationModel
import com.resqfood.data.pref.SaleModel
import com.resqfood.databinding.FragmentHomeBinding

// ini belum

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
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
        setupSearchView()
    }

    private fun setupRVDonation() {
        viewModel.donation.observe(viewLifecycleOwner) { donation: DonationModel ->
            val adapter = DonationAdapter()
            adapter.submitList(donation.listDonation)
            binding.rvDonation.adapter = adapter
            binding.rvDonation.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.getDonation()
    }

    private fun setupRVSale() {
        viewModel.sale.observe(viewLifecycleOwner) { sale: SaleModel ->
            val adapter = ForSaleAdapter()
            adapter.submitList(sale.sale)
            binding.rvSale.adapter = adapter
            binding.rvSale.layoutManager = GridLayoutManager(requireContext(), 2) // 2 kolom dalam grid
            adapter.setData(sale.sale)
        }
        viewModel.getSale()
    }

    private fun setupSearchView() {
        val searchView = view?.findViewById<SearchView>(R.id.searchView)

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false // Biarkan SearchView menangani submit secara default
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (binding.rvSale.adapter as ForSaleAdapter).filter.filter(newText)
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        setupRVDonation()
        setupRVSale()
        setupSearchView()
    }
}
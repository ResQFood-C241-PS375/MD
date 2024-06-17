package com.resqfood.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.resqfood.R
import com.resqfood.ViewModelFactory
import com.resqfood.data.adapter.DonationAdapter
import com.resqfood.data.adapter.ForSaleAdapter
import com.resqfood.data.response.DonationResponse
import com.resqfood.data.response.SellResponse
//import com.resqfood.data.adapter.ForSaleAdapter
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


        viewModel.search.observe(viewLifecycleOwner) { searchResult ->
            val adapter = ForSaleAdapter()
            adapter.submitList(searchResult?.sells)
            binding.rvSale.adapter = adapter
            binding.rvSale.layoutManager = GridLayoutManager(requireContext(), 2)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty()){
                        viewModel.getSearch(it)
                    } else {
                        viewModel.resetSearch()
                        viewModel.getSale()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if(newText.isNullOrEmpty()){
                        viewModel.resetSearch()
                        viewModel.getSale()
                    }
                }
                return true
            }
        })

//        setupSearchView()

//        binding.tesLogout.setOnClickListener {
//            viewModel.logout()
//        }

//        with(binding){
//            searchView.setupWithSearchBar(searchBar)
//            searchView
//                .editText
//                .setOnEditorActionListener { textView, actionID, event ->
//                    viewModel.getSearch(searchView.text.toString())
//                    searchBar.text = searchView.text
//                    searchView.hide()
//                    Toast.makeText(requireContext(), searchView.text, Toast.LENGTH_LONG).show()
//                    false
//                }
//        }

//        with(binding){
//            searchView.setupWithSearchBar(searchBar)
//            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    val searchQuery = searchView.editText.text.toString()
//                    viewModel.getSearch(searchQuery)
//                    searchBar.setQuery(searchQuery, false) // Set query without submitting
//                    searchView.hide()
//                    Toast.makeText(requireContext(), searchQuery, Toast.LENGTH_LONG).show()
//                    true
//                } else {
//                    false
//                }
//            }
//        }


    }

    private fun setupRVDonation() {
        viewModel.donation.observe(viewLifecycleOwner) { donation: DonationResponse ->
            val adapter = DonationAdapter()
            adapter.submitList(donation.donations)
            binding.rvDonation.adapter = adapter
            binding.rvDonation.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.getDonation()
    }

    private fun setupRVSale() {
        viewModel.sale.observe(viewLifecycleOwner) { sale: SellResponse ->
            val adapter = ForSaleAdapter()
            adapter.submitList(sale.sells)
            binding.rvSale.adapter = adapter
            binding.rvSale.layoutManager = GridLayoutManager(requireContext(), 2) // 2 kolom dalam grid
        }
        viewModel.getSale()
    }

    private fun setupSearchView() {
//        val searchView = view?.findViewById<SearchView>(R.id.searchView)
//
//        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false // Biarkan SearchView menangani submit secara default
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
////                (binding.rvSale.adapter as ForSaleAdapter).filter.filter(newText)
//                return true
//            }
//        })
    }

    override fun onResume() {
        super.onResume()
        setupRVDonation()
        setupRVSale()
//        setupSearchView()
    }
}
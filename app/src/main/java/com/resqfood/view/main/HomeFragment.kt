package com.resqfood.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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

class HomeFragment : Fragment(), DonationAdapter.OnItemClickListener, ForSaleAdapter.OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var donationAdapter: DonationAdapter
    private lateinit var saleAdapter: ForSaleAdapter

    companion object {
        private const val TAG = "HomeFragment"
        private const val DONATION_ID = ""
        private const val SALE_ID = ""
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

        val donationAdapter = DonationAdapter(this)
        binding.recyclerViewHorizontal.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHorizontal.adapter = donationAdapter

        val saleAdapter = ForSaleAdapter(this)
        binding.recyclerViewGrid.layoutManager = GridLayoutManager(requireActivity(), 2) // 2 kolom dalam grid
        binding.recyclerViewGrid.adapter = saleAdapter


        findDonation()
        findSale()
    }

    private fun findDonation() {
        val client = ApiConfig.getApiService().getDonation(DONATION_ID)
        client.enqueue(object : Callback<DonationResponse> {
            override fun onResponse(
                call: Call<DonationResponse>,
                response: Response<DonationResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        donationAdapter.submitList(responseBody.donations)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DonationResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun findSale() {
        val client = ApiConfig.getApiService().getSale(SALE_ID)
        client.enqueue(object : Callback<SaleResponse> {
            override fun onResponse(
                call: Call<SaleResponse>,
                response: Response<SaleResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        saleAdapter.submitList(responseBody.sales)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SaleResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    override fun onItemClick(donation: DonationModel) {
        val intent = Intent(activity, DetailDonationActivity::class.java).apply {
            putExtra("donation_title", donation.title)
            putExtra("donation_image", donation.image)
        }
        startActivity(intent)
    }

    override fun onItemClick(sale: ForSaleModel) {
        val intent = Intent(activity, DetailSaleActivity::class.java).apply {
            putExtra("sale_title", sale.title)
            putExtra("sale_image", sale.image)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
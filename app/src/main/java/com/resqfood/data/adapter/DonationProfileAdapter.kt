package com.resqfood.data.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.resqfood.data.response.DonationsItem
import com.resqfood.databinding.CardDonationBinding
import com.resqfood.databinding.CardDonationUserBinding
import com.resqfood.databinding.CardForSaleBinding
import com.resqfood.view.profile.ProfileViewModel

class DonationProfileAdapter(private val viewModel: ProfileViewModel) : ListAdapter<DonationsItem, DonationProfileAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(private val binding: CardDonationUserBinding, private val viewModel: ProfileViewModel) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DonationsItem) {
            binding.donationTitle.text = item.title
            Glide.with(binding.root)
                .load(item.image)
                .into(binding.donationImgUrl)
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(item.donationId)
//                viewModel.deleteDonation(item.donationId)
                Log.d("usersid",item.donationId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardDonationUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DonationsItem>() {
            override fun areItemsTheSame(oldItem: DonationsItem, newItem: DonationsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DonationsItem, newItem: DonationsItem): Boolean {
                return oldItem == newItem
            }
        }
        const val PARCEL_NAME = "data"
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }
}
package com.resqfood.data.adapter

import android.content.Intent
import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.resqfood.data.response.Donation
import com.resqfood.data.response.Users
//import com.resqfood.data.response.DonationsItem
import com.resqfood.databinding.CardDonationBinding
import com.resqfood.view.post_detail.DetailDonationActivity

// Nunggu CC baru di benerin lagi adapternya

class DonationAdapter : ListAdapter<Donation, DonationAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: CardDonationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Donation) {
            binding.donationTitle.text = item.title
            Glide.with(binding.root)
                .load(item.image)
                .into(binding.donationImgUrl)
            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailDonationActivity::class.java)
                intent.putExtra(DetailDonationActivity.EXTRA_ID,item)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardDonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Donation>() {
            override fun areItemsTheSame(oldItem: Donation, newItem: Donation): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Donation, newItem: Donation): Boolean {
                return oldItem == newItem
            }
        }
//        val LIST_DONATION = "donation_detail"
    }
}
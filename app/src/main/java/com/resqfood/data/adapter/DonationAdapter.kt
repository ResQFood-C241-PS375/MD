package com.resqfood.data.adapter

import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.resqfood.data.pref.DonationModel
import com.resqfood.databinding.CardDonationBinding

// Nunggu CC baru di benerin lagi adapternya

class DonationAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<DonationModel, DonationAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(donation: DonationModel)
    }

    inner class ViewHolder(private val binding: CardDonationBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(donationModel: DonationModel) {
            binding.donationImgUrl.setImageResource(donationModel.image) // Ganti dengan properti yang sesuai
            binding.donationTitle.text = donationModel.title // Ganti dengan endpoint properti yang sesuai
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardDonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DonationModel>() {
            override fun areItemsTheSame(oldItem: DonationModel, newItem: DonationModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DonationModel, newItem: DonationModel): Boolean {
                return oldItem == newItem
            }
        }
        const val PARCEL_NAME = "data"
    }
}
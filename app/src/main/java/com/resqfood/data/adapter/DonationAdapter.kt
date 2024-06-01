package com.resqfood.data.adapter

import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.resqfood.data.pref.DonationModel
import com.resqfood.databinding.CardDonationBinding

// Nunggu CC baru di benerin lagi adapternya

class DonationAdapter(
    private val listDonation: ArrayList<DonationModel>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<DonationAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(donation: DonationModel)
    }

    inner class ViewHolder(private val binding: CardDonationBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(listDonation[adapterPosition])
            }
        }

        fun bind(donationModel: DonationModel) {
            binding.donationImgUrl.setImageResource(donationModel.image) // Ganti dengan metode pemuatan gambar yang sesuai
            binding.donationTitle.text = donationModel.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardDonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listDonation.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listDonation[position])
    }

//    companion object {
//        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListDonationItem>() {
//            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListDonationItem): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListDonationItem): Boolean {
//                return oldItem == newItem
//            }
//        }
//        const val PARCEL_NAME = "data"
//    }
}
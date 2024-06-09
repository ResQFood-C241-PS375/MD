//package com.resqfood.data.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.resqfood.databinding.CardForSaleBinding
//
//class DonationProfileAdapter(private val listener: DonationActionListener) : ListAdapter<ListDonationProfileItem, DonationProfileAdapter.ViewHolder>(DIFF_CALLBACK) {
//
//    inner class ViewHolder(private val binding: CardForSaleBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: ListDonationProfileItem) {
//            binding.forsaleTitle.text = item.title
//            Glide.with(binding.root)
//                .load(item.image)
//                .into(binding.forsaleImgUrl)
//            binding.root.setOnClickListener {
//                // ingin memunculkan popup yg isinya button
//                listener.onDeleteDonation(item.id)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = CardForSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//    companion object {
//        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListDonationProfileItem>() {
//            override fun areItemsTheSame(oldItem: ListDonationProfileItem, newItem: ListDonationProfileItem): Boolean {
//                return oldItem == newItem
//            }
//
//            override fun areContentsTheSame(oldItem: ListDonationProfileItem, newItem: ListDonationProfileItem): Boolean {
//                return oldItem == newItem
//            }
//        }
//        const val PARCEL_NAME = "data"
//    }
//}
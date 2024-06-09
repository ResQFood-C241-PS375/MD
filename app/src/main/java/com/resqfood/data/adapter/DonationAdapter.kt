//package com.resqfood.data.adapter
//
//import android.content.Intent
//import android.location.GnssAntennaInfo.Listener
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.resqfood.data.pref.DonationModel
//import com.resqfood.databinding.CardDonationBinding
//import com.resqfood.view.post_detail.DetailDonationActivity
//
//// Nunggu CC baru di benerin lagi adapternya
//
//class DonationAdapter : ListAdapter<ListDonationItem, DonationAdapter.ViewHolder>(DIFF_CALLBACK) {
//
//    class ViewHolder(private val binding: CardDonationBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: ListDonationItem) {
//            binding.donationTitle.text = item.name
//            Glide.with(binding.root)
//                .load(item.image)
//                .into(binding.donationImgUrl)
//            binding.root.setOnClickListener {
//                val context = binding.root.context
//                val intent = Intent(context, DetailDonationActivity::class.java)
//                intent.putExtra(PARCEL_NAME, item)
//                itemView.context.startActivity(intent)
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return currentList.size
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = CardDonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//    companion object {
//        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListDonationItem>() {
//            override fun areItemsTheSame(oldItem: ListDonationItem, newItem: ListDonationItem): Boolean {
//                return oldItem == newItem
//            }
//
//            override fun areContentsTheSame(oldItem: ListDonationItem, newItem: ListDonationItem): Boolean {
//                return oldItem == newItem
//            }
//        }
//        const val PARCEL_NAME = "data"
//    }
//}
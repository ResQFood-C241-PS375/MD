package com.resqfood.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.resqfood.databinding.CardForSaleBinding

class ForSaleProfileAdapter : ListAdapter<ListSaleProfileItem, ForSaleProfileAdapter.ViewHolder>(ForSaleProfileAdapter.DIFF_CALLBACK) {

    class ViewHolder(private val binding: CardForSaleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListSaleProfileItem) {
            binding.forsaleTitle.text = item.title
            Glide.with(binding.root)
                .load(item.image)
                .into(binding.forsaleImgUrl)
            binding.root.setOnClickListener {
                //
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardForSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListSaleProfileItem>() {
            override fun areItemsTheSame(oldItem: ListSaleProfileItem, newItem: ListSaleProfileItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListSaleProfileItem, newItem: ListSaleProfileItem): Boolean {
                return oldItem == newItem
            }
        }
        const val PARCEL_NAME = "data"
    }
}
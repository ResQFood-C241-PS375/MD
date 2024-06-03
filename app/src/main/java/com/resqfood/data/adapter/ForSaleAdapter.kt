package com.resqfood.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.resqfood.data.pref.ForSaleModel
import com.resqfood.databinding.CardForSaleBinding

// Nunggu CC baru dibenerin lagi

class ForSaleAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<ForSaleModel, ForSaleAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(sale : ForSaleModel)
    }

    inner class ViewHolder(private val binding: CardForSaleBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(item: ForSaleModel) {
            binding.forsaleImgUrl.setImageResource(item.image) // Ganti dengan properti yang sesuai
            binding.forsaleTitle.text = item.title // Ganti dengan properti yang sesuai
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ForSaleModel>() {
            override fun areItemsTheSame(oldItem: ForSaleModel, newItem: ForSaleModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ForSaleModel, newItem: ForSaleModel): Boolean {
                return oldItem == newItem
            }
        }
        const val PARCEL_NAME = "data"
    }
}
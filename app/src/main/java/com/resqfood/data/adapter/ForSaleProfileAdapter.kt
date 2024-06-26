package com.resqfood.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.resqfood.data.response.DonationsItem
import com.resqfood.data.response.SellsItems
import com.resqfood.databinding.CardForSaleBinding
import com.resqfood.databinding.CardSellUserBinding
import com.resqfood.view.profile.ProfileViewModel

class ForSaleProfileAdapter(private val viewModel: ProfileViewModel) : ListAdapter<SellsItems, ForSaleProfileAdapter.ViewHolder>(DIFF_CALLBACK) {

//    private lateinit var onItemClickCallback: ForSaleProfileAdapter.OnItemClickCallback
//
//    fun setOnItemClickCallback(onItemClickCallback: ForSaleProfileAdapter.OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(private val binding: CardSellUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SellsItems) {
            binding.sellTitle.text = item.title
            binding.sellExpired.text = item.expire
            Glide.with(binding.root)
                .load(item.sellImg)
                .into(binding.sellImgUrl)
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(item.sellId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardSellUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SellsItems>() {
            override fun areItemsTheSame(oldItem: SellsItems, newItem: SellsItems): Boolean {
                return oldItem.sellId == newItem.sellId
            }

            override fun areContentsTheSame(
                oldItem: SellsItems,
                newItem: SellsItems
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }
}
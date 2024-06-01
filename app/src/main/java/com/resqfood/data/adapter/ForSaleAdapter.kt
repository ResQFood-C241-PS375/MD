package com.resqfood.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.resqfood.data.pref.ForSaleModel
import com.resqfood.databinding.CardForSaleBinding

// Nunggu CC baru dibenerin lagi

class ForSaleAdapter(
    private val listForSale: List<ForSaleModel>,
    private val listener: ForSaleAdapter.OnItemClickListener
) : RecyclerView.Adapter<ForSaleAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(forsale : ForSaleModel)
    }

    inner class ViewHolder(private val binding: CardForSaleBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(listForSale[adapterPosition])
            }
        }

        fun bind(item: ForSaleModel) {
            binding.forsaleImgUrl.setImageResource(item.image)
            binding.forsaleTitle.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardForSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listForSale.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listForSale[position])
    }

//    companion object {
//        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListSaleItem>() {
//            override fun areItemsTheSame(oldItem: ListSaleItem, newItem: ListSaleItem): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: ListSaleItem, newItem: ListSaleItem): Boolean {
//                return oldItem == newItem
//            }
//        }
//        const val PARCEL_NAME = "data"
//    }
}
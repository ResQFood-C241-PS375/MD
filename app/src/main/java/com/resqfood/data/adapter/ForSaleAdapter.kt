package com.resqfood.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.resqfood.data.pref.ForSaleModel
import com.resqfood.databinding.CardForSaleBinding

class ForSaleAdapter(private val listForSale: List<ForSaleModel>) : RecyclerView.Adapter<ForSaleAdapter.ViewHolder>() {
    class ViewHolder(private val binding: CardForSaleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ForSaleModel) {
            binding.forsaleImgUrl.setImageResource(item.image) // Ganti dengan metode pemuatan gambar yang sesuai
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
}
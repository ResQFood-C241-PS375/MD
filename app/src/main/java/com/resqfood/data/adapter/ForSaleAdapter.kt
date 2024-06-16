package com.resqfood.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.resqfood.data.response.Sell
import com.resqfood.databinding.CardForSaleBinding
import com.resqfood.view.post_detail.DetailSaleActivity

// Nunggu CC baru dibenerin lagi

class ForSaleAdapter : ListAdapter<Sell, ForSaleAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: CardForSaleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Sell) {
            binding.forsaleTitle.text = item.title
            Glide.with(binding.root)
                .load(item.sellImg)
                .into(binding.forsaleImgUrl)
            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailSaleActivity::class.java)
                intent.putExtra(DetailSaleActivity.EXTRA_ID, item)
                binding.root.context.startActivity(intent)
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Sell>() {
            override fun areItemsTheSame(oldItem: Sell, newItem: Sell): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Sell, newItem: Sell): Boolean {
                return oldItem == newItem
            }
        }
//        const val PARCEL_NAME = "data"
    }

//    fun setData(list: List<ListSaleItem>) {
//        this.unfilteredData = list
//        submitList(list)
//    }


//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val filteredList = if (constraint.isNullOrEmpty()) {
//                    unfilteredData
//                } else {
//                    val filterPattern = constraint.toString().lowercase().trim()
//                    unfilteredData.filter {
//                        it.name.lowercase().contains(filterPattern)
//                    }
//                }
//                return FilterResults().apply { values = filteredList }
//
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                submitList(results?.values as? List<ListSaleItem>)
//            }
//        }
//    }
}
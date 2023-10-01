package com.example.moHaeng

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.RecyclerviewProductRankingBinding
import com.example.moHaeng.databinding.RecyclerviewRecommendItemBinding

class RankingAdapter (private val itemList: List<ProductItem>) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewProductRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(private val binding: RecyclerviewProductRankingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductItem) {
            binding.apply {
                shopName.text = item.shopName
                productName.text = item.productName
                priceRate.text = item.priceRate
                productPrice.text = item.productPrice
                realPrice.text = item.realPrice
                rankingTag.text = item.rankingTag
            }
        }
    }
}

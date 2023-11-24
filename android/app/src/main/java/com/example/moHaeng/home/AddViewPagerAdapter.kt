package com.example.moHaeng.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.ItemSliderBinding
import com.example.moHaeng.databinding.RecyclerviewRecommendItemBinding
import com.example.moHaeng.productSearch.ProductItem

class AddViewPagerAdapter(var adList: ArrayList<Int>) : RecyclerView.Adapter<AddViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = adList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return adList.size
    }

    inner class ViewHolder(private val binding: ItemSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            binding.apply {
                binding.sliderImage.setImageResource(item)
            }
        }
    }

}
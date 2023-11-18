package com.example.moHaeng

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.RecyclerviewCategoryButtonBinding
import com.example.moHaeng.productSearch.CategoryItem

class CategoryButtonAdapter(private val itemList: List<CategoryItem>) : RecyclerView.Adapter<CategoryButtonAdapter.CategoryButtonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryButtonViewHolder {
        val binding = RecyclerviewCategoryButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryButtonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryButtonViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class CategoryButtonViewHolder(private val binding: RecyclerviewCategoryButtonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryItem) {
            binding.apply {
                categoryName.text = item.categoryName
            }
        }
    }
}

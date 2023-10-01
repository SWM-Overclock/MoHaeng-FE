package com.example.moHaeng

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.RecyclerviewCategoryListBinding
import com.example.moHaeng.databinding.RecyclerviewCategoryButtonBinding

class CategoryAdapter(private val itemList: List<CategoryItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val categoryList = 1
    private val categoryButton = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            categoryList -> {
                val binding = RecyclerviewCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CategoryListViewHolder(binding)
            }
            categoryButton -> {
                val binding = RecyclerviewCategoryButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CategoryButtonViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        when (holder) {
            is CategoryListViewHolder -> holder.bind(item)
            is CategoryButtonViewHolder -> holder.bind(item)
            // Add cases for other view holders if needed
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        // Determine the view type based on your category logic
        // For now, let's assume that even positions are Category List and odd positions are Category Button
        return if (position % 2 == 0) categoryList else categoryButton
    }

    inner class CategoryListViewHolder(private val binding: RecyclerviewCategoryListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryItem) {
            binding.apply {
                categoryName.text = item.categoryName
                categoryImage.setImageResource(R.drawable.ic_launcher_background)
            }
        }
    }

    inner class CategoryButtonViewHolder(private val binding: RecyclerviewCategoryButtonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryItem) {
            binding.apply{
                categoryName.text = item.categoryName
            }

        }
    }
}

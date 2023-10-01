package com.example.moHaeng

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.RecyclerviewCategoryListBinding

class CategoryAdapter(private val itemList: List<CategoryItem>) : RecyclerView.Adapter<CategoryAdapter.CategoryListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val binding = RecyclerviewCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class CategoryListViewHolder(private val binding: RecyclerviewCategoryListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryItem) {
            binding.apply {
                categoryName.text = item.categoryName
                categoryImage.setImageResource(R.drawable.ic_launcher_background)
            }
        }
    }
}

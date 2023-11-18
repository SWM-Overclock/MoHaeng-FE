package com.example.moHaeng.productSearch

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.CategoryButtonFragment
import com.example.moHaeng.R
import com.example.moHaeng.databinding.ActivityProductRankingBinding

class ProductRankingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductRankingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCategoryButtonFragment()
        setupProductRecyclerView()
    }

    private fun setupCategoryButtonFragment() {
        val categoryButtonFragment = CategoryButtonFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.categoryButtonContainer, categoryButtonFragment)
            .commit()
    }

    private fun setupProductRecyclerView() {
        val recyclerView: RecyclerView = binding.ProductRecyclerView

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        val space = (Resources.getSystem().displayMetrics.density).toInt()
        recyclerView.addItemDecoration(GridSpaceProductDecoration(2, space))

        val itemList = listOf(
            ProductItem("상점1", "상품1", "10%", "5,000", "4,500", true, "1"),
            ProductItem("상점2", "상품2", "5%", "10,000", "9,500", true, "2"),
            ProductItem("상점3", "상품3", "0%", "7,000", "7,000", true, "3"),
            ProductItem("상점3", "상품3", "0%", "7,000", "7,000", true, "3")
        )

        val adapter = ProductRankingAdapter(itemList)
        recyclerView.adapter = adapter
    }
}

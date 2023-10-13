package com.example.moHaeng

import RecommendAdapter
import android.content.res.Resources
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.ActivityDetailBinding
import com.example.moHaeng.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityProductListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupCategoryButtonFragment()

        val recyclerView: RecyclerView = binding.ProductRecyclerView

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        val space = (Resources.getSystem().displayMetrics.density).toInt()
        recyclerView.addItemDecoration(GridSpaceProductDecoration(2, space))

        val itemList = listOf(
            ProductItem("상점1", "상품1", "10%", "5,000", "4,500", true, "1"),
            ProductItem("상점2", "상품2", "5%", "10,000", "9,500", true, "2"),
            ProductItem("상점3", "상품3", "0%", "7,000", "7,000", false, "3"),
            ProductItem("상점3", "상품3", "0%", "7,000", "7,000", false, "3")
        )

        val adapter = RecommendAdapter(itemList)
        recyclerView.adapter = adapter
    }
    private fun setupCategoryButtonFragment() {
        val categoryButtonFragment = CategoryButtonFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.categoryButtonContainer, categoryButtonFragment)
            .commit()
    }

}

class GridSpaceProductDecoration(private val spanCount: Int, private val space: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)     // item position
        // 첫번째 열을 제외하고 좌측 여백 추가
        if (position >= 2) {
            outRect.top = space * 18
        }
    }
}
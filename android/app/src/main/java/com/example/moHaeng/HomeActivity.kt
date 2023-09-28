package com.example.moHaeng

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // RecyclerView 초기화
        val recyclerView: RecyclerView = binding.categoryRecyclerView



        val layoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager


        val categoryList = listOf(
            CategoryItem("카테고리1", R.drawable.ic_launcher_background),
            CategoryItem("카테고리2", R.drawable.ic_launcher_background),
            CategoryItem("카테고리3", R.drawable.ic_launcher_background),
            CategoryItem("카테고리4", R.drawable.ic_launcher_background),
            CategoryItem("카테고리5", R.drawable.ic_launcher_background),
            CategoryItem("카테고리6", R.drawable.ic_launcher_background),
            CategoryItem("카테고리7", R.drawable.ic_launcher_background),
            CategoryItem("카테고리8", R.drawable.ic_launcher_background),
            CategoryItem("카테고리9", R.drawable.ic_launcher_background),
            CategoryItem("카테고리0", R.drawable.ic_launcher_background)
        )

        val adapter = CategoryAdapter(categoryList)
        recyclerView.adapter = adapter

        recyclerView?.run {
            val spanCount = categoryList.size / 2
            val space = (Resources.getSystem().displayMetrics.density).toInt()
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
        }
    }
}
// GridLayoutManager의 간격 조절
class GridSpaceItemDecoration(private val spanCount: Int, private val space: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)     // item position
        val row = position % 2 + 1      // item row

        // 첫 행 제외하고 상단 여백 추가
        if (row == 2) {
            outRect.top = space * 2
        }
        // 첫번째 열을 제외하고 좌측 여백 추가
        if (position >= 2) {
            outRect.left = space * 8
        }
    }
}

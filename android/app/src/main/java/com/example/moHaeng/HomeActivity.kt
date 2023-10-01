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
        val recyclerViewList: RecyclerView = binding.categoryRecyclerView
        val recyclerViewButton: RecyclerView = binding.categoryRecyclerView
        val recyclerViewRanking: RecyclerView = binding.rankingRecyclerView


        val layoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val layoutManager3 = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerViewList.layoutManager = layoutManager
        recyclerViewButton.layoutManager = layoutManager2
        recyclerViewRanking.layoutManager = layoutManager3


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

        val itemList = listOf(
            ProductItem("상점1", "상품1", "10%", "5,000", "4,500", true, "1"),
            ProductItem("상점2", "상품2", "5%", "10,000", "9,500", true, "2"),
            ProductItem("상점3", "상품3", "0%", "7,000", "7,000", false, "3")
        )



        val categoryAdapter = CategoryAdapter(categoryList)
        val rankingAdapter = RankingAdapter(itemList)
        recyclerViewList.adapter = categoryAdapter
        recyclerViewButton.adapter = categoryAdapter
        recyclerViewRanking.adapter = rankingAdapter

        recyclerViewList?.run {
            val spanCount = categoryList.size / 2
            val space = (Resources.getSystem().displayMetrics.density).toInt()
            addItemDecoration(GridSpaceItemDecorationList(spanCount, space))
        }

        recyclerViewRanking?.run {
            val space = (Resources.getSystem().displayMetrics.density).toInt()
            addItemDecoration(GridSpaceItemDecorationRanking(space))
        }
    }
}
// GridLayoutManager의 간격 조절
class GridSpaceItemDecorationList(private val spanCount: Int, private val space: Int) :
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

// 랭킹 recyclerview 간격 조절
class GridSpaceItemDecorationRanking(private val space: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)     // item positionw

        // 첫 행 제외하고 상단 여백 추가
        if (position >= 1) {
            outRect.top = space * 24
        }
    }
}

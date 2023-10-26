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
import java.lang.Math.floor

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val itemList: List<CategoryItem> = generateDummyCategoryData()
    private val productItemList: List<ProductItem> = generateDummyProductData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupCategoryRecyclerView()
        setupCategoryButtonFragment()
        setupRankingRecyclerView()
    }

    private fun setupCategoryRecyclerView() {
        val recyclerViewList: RecyclerView = binding.categoryRecyclerView
        recyclerViewList.layoutManager = GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false)

        val categoryAdapter = CategoryAdapter(itemList)
        recyclerViewList.adapter = categoryAdapter

        val space = (Resources.getSystem().displayMetrics.density).toInt()
        recyclerViewList.addItemDecoration(GridSpaceCategoryList(5, space))
    }

    private fun setupCategoryButtonFragment() {
        val categoryButtonFragment = CategoryButtonFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.categoryButtonContainer, categoryButtonFragment)
            .commit()
    }


    private fun setupRankingRecyclerView() {
        val recyclerViewRanking: RecyclerView = binding.rankingRecyclerView
        recyclerViewRanking.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val rankingAdapter = RankingAdapter(productItemList)
        recyclerViewRanking.adapter = rankingAdapter

        val space = (Resources.getSystem().displayMetrics.density).toInt()
        recyclerViewRanking.addItemDecoration(GridSpaceItemDecorationRanking(space))
    }

    private fun generateDummyCategoryData(): List<CategoryItem> {
        return listOf(
            CategoryItem("카테고리1", R.drawable.ic_launcher_background),
            CategoryItem("카테고리2", R.drawable.ic_launcher_background),
            CategoryItem("카테고리3", R.drawable.ic_launcher_background),
            CategoryItem("카테고리4", R.drawable.ic_launcher_background),
            CategoryItem("카테고리5", R.drawable.ic_launcher_background),
            CategoryItem("카테고리6", R.drawable.ic_launcher_background),
            CategoryItem("카테고리7", R.drawable.ic_launcher_background),
            CategoryItem("카테고리8", R.drawable.ic_launcher_background),
            CategoryItem("카테고리9", R.drawable.ic_launcher_background),
            CategoryItem("카테고리0", R.drawable.ic_launcher_background),
            CategoryItem("카테고리6", R.drawable.ic_launcher_background),
            CategoryItem("카테고리7", R.drawable.ic_launcher_background),
            CategoryItem("카테고리8", R.drawable.ic_launcher_background),
            CategoryItem("카테고리9", R.drawable.ic_launcher_background),
            CategoryItem("카테고리0", R.drawable.ic_launcher_background)
        )
    }

    private fun generateDummyProductData(): List<ProductItem> {
        return listOf(
            ProductItem("상점1", "상품1", "10%", "5,000", "4,500", true, "1"),
            ProductItem("상점2", "상품2", "5%", "10,000", "9,500", true, "2"),
            ProductItem("상점3", "상품3", "0%", "7,000", "7,000", false, "3"),
            ProductItem("상점1", "상품1", "10%", "5,000", "4,500", true, "1"),
            ProductItem("상점2", "상품2", "5%", "10,000", "9,500", true, "2")
        )
    }
}

// GridLayoutManager의 간격 조절
class GridSpaceCategoryList(private val spanCount: Int, private val space: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view).toDouble()  // item position
        val row = kotlin.math.floor(position / 5)  // item row


        val column = position % spanCount

        // 각 아이템의 폭
        val itemWidth = (parent.width - space * (spanCount - 1)) / spanCount

        // 현재 아이템의 시작 위치
        val start = column * (itemWidth + space)

        // 좌측 여백 및 우측 여백을 설정
        outRect.left = start.toInt()
        outRect.right = (parent.width - start - itemWidth).toInt()

        // 첫 행 제외하고 상단 여백 추가
        if (row != 0.0) {
            outRect.top = space * 2
        }
        // 첫번째 열을 제외하고 좌측 여백 추가
        outRect.left = space * 8
        outRect.right = space * 8
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
        val row = position % 2  // item row

        // 첫 행 제외하고 상단 여백 추가
        if (position >= 1) {
            outRect.top = space * 24
        }

    }
}

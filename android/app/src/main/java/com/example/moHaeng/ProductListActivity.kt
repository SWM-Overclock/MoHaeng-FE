package com.example.moHaeng

import RecommendAdapter
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 프래그먼트 설정 및 제품 리사이클러뷰 설정 호출
        setupFragments()
        setupProductRecyclerView()
    }

    private fun setupFragments() {
        // 카테고리 버튼 프래그먼트와 소팅 프래그먼트를 설정
        setupFragment(CategoryButtonFragment(), R.id.categoryButtonContainer)
        setupFragment(SortedFragment(), R.id.sortButtonContainer)
    }

    private fun setupFragment(fragment: Fragment, containerId: Int) {
        // 프래그먼트를 지정된 컨테이너에 교체
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    private fun setupProductRecyclerView() {
        val recyclerView: RecyclerView = binding.ProductRecyclerView

        // 그리드 레이아웃 매니저 설정
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        // 간격 설정
        val space = (Resources.getSystem().displayMetrics.density).toInt()
        recyclerView.addItemDecoration(GridSpaceProductDecoration(2, space))

        // 상품 아이템 리스트 생성
        val itemList = listOf(
            ProductItem("상점1", "상품1", "10%", "5,000", "4,500", true, "1"),
            ProductItem("상점2", "상품2", "5%", "10,000", "9,500", true, "2"),
            ProductItem("상점3", "상품3", "0%", "7,000", "7,000", false, "3"),
            ProductItem("상점3", "상품3", "0%", "7,000", "7,000", false, "3")
        )

        // 어댑터 설정
        val adapter = RecommendAdapter(itemList)
        recyclerView.adapter = adapter
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
        // 아이템 위치 및 행 계산
        val position = parent.getChildAdapterPosition(view)
        val row = position % spanCount

        // 간격 적용
        if (position >= 2) {
            outRect.top = space * 18
        }

        if (row != 0) {
            outRect.left = space * 4
        } else {
            outRect.right = space * 4
        }
    }
}

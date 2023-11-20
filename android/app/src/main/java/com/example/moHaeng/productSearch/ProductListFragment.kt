package com.example.moHaeng.productSearch

import RecommendAdapter
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.CategoryButtonFragment
import com.example.moHaeng.R
import com.example.moHaeng.databinding.FragmentProductListBinding
import com.example.moHaeng.sort.SortedFragment

class ProductListFragment : Fragment() {


    private lateinit var binding: FragmentProductListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater, container, false)

        setupFragments()
        setupProductRecyclerView()

        return binding.root
    }

    private fun setupFragments() {
        // 카테고리 버튼 프래그먼트와 소팅 프래그먼트를 설정
        setupFragment(CategoryButtonFragment(), R.id.categoryButtonContainer)
        setupFragment(SortedFragment(), R.id.sortButtonContainer)
    }

    private fun setupFragment(fragment: Fragment, containerId: Int) {

        childFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    private fun setupProductRecyclerView() {
        val recyclerView: RecyclerView = binding.ProductRecyclerView

        // 그리드 레이아웃 매니저 설정
        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = layoutManager

        // 간격 설정
        val space = (Resources.getSystem().displayMetrics.density).toInt()
        recyclerView.addItemDecoration(DecorationProductRecycler(2, space))

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
package com.example.moHaeng.home

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.CategoryAdapter
import com.example.moHaeng.CategoryButtonFragment
import com.example.moHaeng.MainActivity
import com.example.moHaeng.productSearch.CategoryItem
import com.example.moHaeng.productSearch.ProductItem
import com.example.moHaeng.R
import com.example.moHaeng.productSearch.RankingAdapter
import com.example.moHaeng.databinding.FragmentHomeBinding
import com.example.moHaeng.location.EditLocationFragment
import com.example.moHaeng.location.SetLocationFragment
import com.example.moHaeng.login.JwtCheck
import com.example.moHaeng.productSearch.ProductRankingFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val itemList: List<CategoryItem> = generateDummyCategoryData()
    private val productItemList: List<ProductItem> = generateDummyProductData()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupCategoryRecyclerView()
        setupCategoryButtonFragment()
        setupRankingRecyclerView()
        setupLocationContainer()

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rankingFindMoreButton.setOnClickListener {
            setupRankingFindMoreButton()
        }
    }

    //rankingFindMoreButton버튼을 누르면 지금 homeFragment가 있는 activity의 fragment를 변경
    private fun setupRankingFindMoreButton() {
        (activity as MainActivity).setFragment("productRanking", ProductRankingFragment())
    }


    private fun setupCategoryRecyclerView() {
        val recyclerViewList: RecyclerView = binding.categoryRecyclerView
        recyclerViewList.layoutManager = GridLayoutManager(requireContext(), 5, GridLayoutManager.VERTICAL, false)

        val categoryAdapter = CategoryAdapter(itemList)
        recyclerViewList.adapter = categoryAdapter

        val space = (Resources.getSystem().displayMetrics.density).toInt()
        recyclerViewList.addItemDecoration(GridSpaceCategoryList(5, space))
    }

    private fun setupCategoryButtonFragment() {
        val categoryButtonFragment = CategoryButtonFragment()

        childFragmentManager.beginTransaction()
            .replace(R.id.categoryButtonContainer, categoryButtonFragment)
            .commit()
    }


    private fun setupRankingRecyclerView() {
        val recyclerViewRanking: RecyclerView = binding.rankingRecyclerView
        recyclerViewRanking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val rankingAdapter = RankingAdapter(productItemList)
        recyclerViewRanking.adapter = rankingAdapter

        val space = (Resources.getSystem().displayMetrics.density).toInt()
        recyclerViewRanking.addItemDecoration(GridSpaceItemDecorationRanking(space))
    }

    private fun generateDummyCategoryData(): List<CategoryItem> {
        return listOf(
            CategoryItem("편의점", R.drawable.category_convi),
            CategoryItem("카페", R.drawable.category_cafe),
            CategoryItem("음식점", R.drawable.category_food),
            CategoryItem("문화생활", R.drawable.category_culture),
            CategoryItem("과자", R.drawable.category_snack),
            CategoryItem("음료", R.drawable.category_drink),
            CategoryItem("아이스크림", R.drawable.category_ice),
            CategoryItem("즉석식품", R.drawable.category_instant),
            CategoryItem("유제품", R.drawable.category_daily),
            CategoryItem("전체보기", R.drawable.category_all)
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

    //locationContainer늘 누르면 locationFragment로 이동하는 함수
    private fun setupLocationContainer() {
        binding.locationContainer.setOnClickListener {
            (activity as MainActivity).setFragment("location", SetLocationFragment())
        }
    }

}





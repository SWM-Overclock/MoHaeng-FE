package com.example.moHaeng.home

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.CategoryAdapter
import com.example.moHaeng.CategoryButtonFragment
import com.example.moHaeng.CategoryItem
import com.example.moHaeng.ProductItem
import com.example.moHaeng.R
import com.example.moHaeng.RankingAdapter
import com.example.moHaeng.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val itemList: List<CategoryItem> = generateDummyCategoryData()
    private val productItemList: List<ProductItem> = generateDummyProductData()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setupCategoryRecyclerView()
        setupCategoryButtonFragment()
        setupRankingRecyclerView()

        return view
    }

//    private fun setBottomNavigationView() {
//        binding.navigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.naviHomeFragment -> {
//                    true
//                }
//                R.id.naviMapFragment -> {
//                    val intent = Intent(requireContext(), LocationPermissionActivity::class.java)
//                    startActivity(intent)
//                    true
//                }
//                // 나머지 아이템에 대한 처리도 동일하게 구현합니다.
//                else -> false
//            }
//        }
//    }


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





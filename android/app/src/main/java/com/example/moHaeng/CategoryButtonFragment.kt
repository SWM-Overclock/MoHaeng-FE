package com.example.moHaeng

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.FragmentCategoryButtonBinding
import com.example.moHaeng.productSearch.CategoryItem


class CategoryButtonFragment : Fragment() {
    private lateinit var binding: FragmentCategoryButtonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryButtonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryButtonRecyclerView()
    }

    private fun setupCategoryButtonRecyclerView() {
        val recyclerViewList: RecyclerView = binding.categoryButtonRecyclerView
        recyclerViewList.layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)

        val categoryButtonAdapter = CategoryButtonAdapter(generateDummyCategoryData())
        recyclerViewList.adapter = categoryButtonAdapter

        val space = (Resources.getSystem().displayMetrics.density * 4).toInt() // Adjust the space value as needed
        recyclerViewList.addItemDecoration(GridSpaceCategoryButton(space))
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
}

class GridSpaceCategoryButton(private val space: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
    }
}

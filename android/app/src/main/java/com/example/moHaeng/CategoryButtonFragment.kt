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
            CategoryItem("카테고리1", R.drawable.ic_launcher_background),
            CategoryItem("카테고리2", R.drawable.ic_launcher_background),
            CategoryItem("카테고리3", R.drawable.ic_launcher_background),
            CategoryItem("카테고리4", R.drawable.ic_launcher_background),
            CategoryItem("카테고리5", R.drawable.ic_launcher_background),
            CategoryItem("카테고리6", R.drawable.ic_launcher_background),
            CategoryItem("카테고리7", R.drawable.ic_launcher_background),
            CategoryItem("카테고리8", R.drawable.ic_launcher_background),
            CategoryItem("카테고리9", R.drawable.ic_launcher_background),
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

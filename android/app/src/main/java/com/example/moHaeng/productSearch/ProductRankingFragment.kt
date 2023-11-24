package com.example.moHaeng.productSearch

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.CategoryButtonFragment
import com.example.moHaeng.MainActivity
import com.example.moHaeng.R
import com.example.moHaeng.databinding.FragmentProductRankingBinding

class ProductRankingFragment : Fragment() {

    private lateinit var binding: FragmentProductRankingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductRankingBinding.inflate(inflater, container, false)

        setupCategoryButtonFragment()
        setupProductRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.backButton.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
    }

    private fun setupCategoryButtonFragment() {
        val categoryButtonFragment = CategoryButtonFragment()

        childFragmentManager.beginTransaction()
            .replace(R.id.categoryButtonContainer, categoryButtonFragment)
            .commit()
    }

    private fun setupProductRecyclerView() {
        val recyclerView: RecyclerView = binding.ProductRecyclerView

        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = layoutManager

        val space = (Resources.getSystem().displayMetrics.density).toInt()
        recyclerView.addItemDecoration(DecorationProductRecycler(2, space))

        val itemList = listOf(
            ProductItem("상점1", "상품1", "10%", "5,000", "4,500", true, "1"),
            ProductItem("상점2", "상품2", "5%", "10,000", "9,500", true, "2"),
            ProductItem("상점3", "상품3", "0%", "7,000", "7,000", true, "3"),
            ProductItem("상점3", "상품3", "0%", "7,000", "7,000", true, "3")
        )

        val adapter = ProductRankingAdapter(itemList)
        recyclerView.adapter = adapter
    }

}
package com.example.moHaeng.productSearch

import RecommendAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.MainActivity
import com.example.moHaeng.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecommendAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
    }

    private fun initRecyclerView() {
        recyclerView = binding.recommendRecyclerView
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        // 예시 데이터
        val itemList = listOf(
            ProductItem("상점1", "상품1", "10%", "5,000", "4,500", true, "1"),
            ProductItem("상점2", "상품2", "5%", "10,000", "9,500", true, "2"),
            ProductItem("상점3", "상품3", "0%", "7,000", "7,000", false, "3")
        )

        // RecommendAdapter를 생성하고 RecyclerView에 연결
        adapter = RecommendAdapter(itemList)
        recyclerView.adapter = adapter
    }
}

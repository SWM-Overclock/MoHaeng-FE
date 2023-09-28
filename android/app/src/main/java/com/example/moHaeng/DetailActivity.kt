package com.example.moHaeng

import RecommendAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // RecyclerView 초기화
        val recyclerView: RecyclerView = binding.recommendRecyclerView

        // LinearLayoutManager를 수평 방향으로 설정
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        // RecyclerView에 표시할 데이터 아이템 리스트 생성 (예시 데이터)
        val itemList = listOf(
            RecommendItem("상점1", "상품1", "10%", "5,000", "4,500", true),
            RecommendItem("상점2", "상품2", "5%", "10,000", "9,500", true),
            RecommendItem("상점3", "상품3", "0%", "7,000", "7,000", false)
        )

        // RecommendAdapter를 생성하고 RecyclerView에 연결
        val adapter = RecommendAdapter(itemList)
        recyclerView.adapter = adapter
    }
}

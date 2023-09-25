package com.example.moHaeng

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val categoryItemList = mutableListOf<CategoryItem>()
        categoryItemList.add(CategoryItem("카테고리1", R.drawable.ic_launcher_background))
        categoryItemList.add(CategoryItem("카테고리2", R.drawable.ic_launcher_background))
        val categoryAdapter = CategoryAdapter(categoryItemList)

        val recyclerView: RecyclerView = findViewById(R.id.categoryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = categoryAdapter
    }
}

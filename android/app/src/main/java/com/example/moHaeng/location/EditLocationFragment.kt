package com.example.moHaeng.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.MainActivity
import com.example.moHaeng.databinding.FragmentEditLocationBinding
import com.example.moHaeng.maps.MapFragment

class EditLocationFragment : Fragment() {

    private lateinit var binding: FragmentEditLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditLocationBinding.inflate(inflater, container, false)

        setEditLocationRecyclerView()
        setupMapContainer()

        return binding.root
    }

    private fun setEditLocationRecyclerView() {
        val recyclerView: RecyclerView = binding.locationListRecyclerView
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        val locationList = listOf(
            LocationItem("1", "집", "서울시 강남구", true),
            LocationItem("2", "회사", "서울시 강남구", false),
            LocationItem("3", "집", "서울시 강남구", false),
            LocationItem("4", "집", "서울시 강남구", false),
            LocationItem("5", "집", "서울시 강남구", false),
            LocationItem("6", "집", "서울시 강남구", false),
            LocationItem("7", "집", "서울시 강남구", false),
            LocationItem("8", "집", "서울시 강남구", false),
            LocationItem("9", "집", "서울시 강남구", false),
            LocationItem("10", "집", "서울시 강남구", false),
            LocationItem("11", "집", "서울시 강남구", false),
            LocationItem("12", "집", "서울시 강남구", false),
            LocationItem("13", "집", "서울시 강남구", false),
            LocationItem("14", "집", "서울시 강남구", false),
            LocationItem("15", "집", "서울시 강남구", false),
            LocationItem("16", "집", "서울시 강남구", false),
            LocationItem("17", "집", "서울시 강남구", false),
            LocationItem("18", "집", "서울시 강남구", false),
            LocationItem("19", "집", "서울시 강남구", false),
            LocationItem("20", "집", "서울시 강남구", false),
            LocationItem("21", "집", "서울시 강남구", false),
            LocationItem("22", "집", "서울시 강남구", false),
        )

        val adapter = EditAdapter(locationList)
        recyclerView.adapter = adapter
    }

    private fun setupMapContainer() {
        binding.locationSetView.setOnClickListener {
            (activity as MainActivity).setFragment("home", MapFragment())
        }
    }

}
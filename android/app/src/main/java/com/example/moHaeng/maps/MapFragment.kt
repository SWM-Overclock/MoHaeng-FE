package com.example.moHaeng.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moHaeng.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        setupMapContainer()

        return binding.root
    }

    //map_container에 kakao map fragment를 추가
    private fun setupMapContainer() {
        val mapFragment = KakaoMapsFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(binding.mapContainer.id, mapFragment).commit()
    }

}
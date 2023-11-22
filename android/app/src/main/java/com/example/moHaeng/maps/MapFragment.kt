package com.example.moHaeng.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moHaeng.KakaoMapsFragment
import com.example.moHaeng.MainActivity
import com.example.moHaeng.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)


        return binding.root
    }

    private fun setupMapContainer() {
        binding.mapContainer.setOnClickListener {
            (activity as MainActivity).setFragment("home", KakaoMapsFragment())
        }
    }

}
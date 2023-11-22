package com.example.moHaeng.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.MainActivity
import com.example.moHaeng.databinding.FragmentSetLocationBinding
import com.example.moHaeng.maps.LocationPermissionUtils
import com.example.moHaeng.maps.MapFragment

class SetLocationFragment : Fragment() {

    private lateinit var binding: FragmentSetLocationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetLocationBinding.inflate(inflater, container, false)

        setSetLocationRecyclerView()
        setupMapContainer()
        setupEditButton()


        return binding.root
    }

    private fun setSetLocationRecyclerView() {
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

        val adapter = LocationAdapter(locationList)
        recyclerView.adapter = adapter
    }

    private fun setupMapContainer() {
        binding.locationSetView.setOnClickListener {
            // 위치 권한 확인
            if (LocationPermissionUtils.checkLocationPermission(requireContext())) {
                // 위치 권한이 있는 경우
                if (LocationPermissionUtils.isLocationServiceEnabled(requireContext())) {
                    // 위치 서비스가 활성화된 경우, 지도 설정
                    (activity as MainActivity).setFragment("home", MapFragment())
                } else {
                    // 위치 서비스가 비활성화된 경우, 사용자에게 메시지 표시
                    Toast.makeText(requireContext(), "위치 서비스가 비활성화되어 있습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // 위치 권한이 없는 경우, 권한 요청
                LocationPermissionUtils.requestLocationPermission(
                    this,
                    SetLocationFragment.LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }
    private fun setupEditButton() {
        binding.editButton.setOnClickListener {
            (activity as MainActivity).setFragment("home", EditLocationFragment())
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

}
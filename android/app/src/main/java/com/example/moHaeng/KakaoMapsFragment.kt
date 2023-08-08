package com.example.moHaeng;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moHaeng.databinding.FragmentKakaoMapsBinding
import com.google.android.gms.maps.model.Marker
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView



class KakaoMapsFragment : Fragment() {

    private lateinit var binding: FragmentKakaoMapsBinding
    private lateinit var mapView: MapView
    private lateinit var marker: Marker

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentKakaoMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Daum MapView 동적으로 추가
        mapView = MapView(requireContext())
        val mapViewContainer = binding.rootLayout
        mapViewContainer.addView(mapView)

        // 마커 추가
        addMarker()
    }

    private fun addMarker() {

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);

        val markerPoint = MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633)

        val marker = MapPOIItem()
        marker.itemName = "Default Marker"
        marker.tag = 0
        marker.mapPoint = markerPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.

        marker.selectedMarkerType =
            MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.


        mapView.addPOIItem(marker)
    }
}

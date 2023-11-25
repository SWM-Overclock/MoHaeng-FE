package com.example.moHaeng.maps

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.moHaeng.BuildConfig
import com.example.moHaeng.databinding.FragmentKakaoMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapReverseGeoCoder
import net.daum.mf.map.api.MapView

class KakaoMapsFragment : Fragment() {

    private lateinit var binding: FragmentKakaoMapsBinding
    private lateinit var mapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentMarker: MapPOIItem
    private var locationSelectedListener: OnLocationSelectedListener? = null

    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 500 // 업데이트 간격 (0.5초)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 현재 위치 전달 리스너 초기화
        mapView = MapView(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKakaoMapsBinding.inflate(inflater, container, false)

        binding.rootLayout.addView(mapView)

        // FusedLocationProviderClient 초기화
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // 초기 마커 추가
        addCurrentLocationMarker()

        // 지도 이동 이벤트 리스너 등록
        //
//        mapView.setMapViewEventListener(MapViewEventListener())

        handler.postDelayed(object : Runnable {
            override fun run() {
                updateMarker()
                handler.postDelayed(this, updateInterval.toLong())
            }
        }, updateInterval.toLong())

        return binding.root
    }

    private fun updateMarker() {
        // 현재 지도 중심 위치 가져오기
        val mapCenter = mapView.mapCenterPoint.mapPointGeoCoord

        // 마커 업데이트
        currentMarker.mapPoint = MapPoint.mapPointWithGeoCoord(mapCenter.latitude, mapCenter.longitude)
        mapView.removePOIItem(currentMarker)
        mapView.addPOIItem(currentMarker)

        // 주소 정보 업데이트
        getCurrentAddress(mapView.mapCenterPoint)
    }


    private fun addCurrentLocationMarker() {
        // 위치 권한 확인
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // FusedLocationProviderClient를 사용하여 현재 위치 가져오기
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    // 현재 위치의 위도와 경도를 가져와 지도의 중심으로 설정
                    mapView.setMapCenterPoint(
                        MapPoint.mapPointWithGeoCoord(it.latitude, it.longitude),
                        true
                    )

                    // 현재 위치 마커 추가
                    currentMarker = addMarker(it.latitude, it.longitude)
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
        }
    }

    private fun addMarker(latitude: Double, longitude: Double): MapPOIItem {
        val markerPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)

        val marker = MapPOIItem()
        marker.itemName = "설정 위치"
        marker.tag = 0
        marker.mapPoint = markerPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin

        mapView.addPOIItem(marker)

        return marker
    }




    // 현재 위치 전달 메서드
    private fun sendCurrentLocation(latitude: Double, longitude: Double, address: String) {
        locationSelectedListener?.onLocationSelected(latitude, longitude, address)
    }

    fun getCurrentLocation(): LatLng? {
        val centerPoint = mapView.mapCenterPoint
        return LatLng(centerPoint.mapPointGeoCoord.latitude, centerPoint.mapPointGeoCoord.longitude)
    }

    interface OnLocationSelectedListener {
        fun onLocationSelected(latitude: Double, longitude: Double, address: String)
    }

    private fun getCurrentAddress(currentMapPoint: MapPoint) {
        val activity = activity

        if (activity != null) {
            val reverseGeoCoder = MapReverseGeoCoder(
                BuildConfig.KAKAO_APP_KEY,
                currentMapPoint,
                object : MapReverseGeoCoder.ReverseGeoCodingResultListener {
                    override fun onReverseGeoCoderFailedToFindAddress(p0: MapReverseGeoCoder?) {
                        // 현재 위도, 경도와 주소를 알 수 없음으로 처리
                        sendCurrentLocation(
                            currentMapPoint.mapPointGeoCoord.latitude,
                            currentMapPoint.mapPointGeoCoord.longitude,
                            "알 수 없음"
                        )
                    }

                    override fun onReverseGeoCoderFoundAddress(p0: MapReverseGeoCoder?, p1: String?) {
                        // 주소를 성공적으로 찾은 경우의 처리
                        p1?.let {
                            sendCurrentLocation(
                                currentMapPoint.mapPointGeoCoord.latitude,
                                currentMapPoint.mapPointGeoCoord.longitude,
                                it
                            )
                        }
                    }
                }, activity
            )
            reverseGeoCoder.startFindingAddress()
        } else {
            // 현재 위도, 경도와 주소를 알 수 없음으로 처리
            sendCurrentLocation(
                currentMapPoint.mapPointGeoCoord.latitude,
                currentMapPoint.mapPointGeoCoord.longitude,
                "알 수 없음"
            )
        }
    }

}

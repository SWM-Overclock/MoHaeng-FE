package com.example.moHaeng.maps

import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.moHaeng.MainActivity
import com.example.moHaeng.R
import com.example.moHaeng.databinding.FragmentMapBinding
import java.util.Locale

class MapFragment : Fragment(), KakaoMapsFragment.OnLocationSelectedListener {

    private lateinit var binding: FragmentMapBinding
    private lateinit var kakaoMapsFragment: KakaoMapsFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        setupMapContainer()

        // event_add_button 클릭 이벤트 처리
        binding.eventAddButton.setOnClickListener {
            // event_add_button을 클릭하면 KakaoMapsFragment에서 현재 위치를 가져와서 처리
            kakaoMapsFragment.getCurrentLocation()?.let { currentLocation ->
                onLocationSelected(
                    currentLocation.latitude,
                    currentLocation.longitude,
                    "Dummy Address" // 여기서는 주소가 없으므로 더미 주소 사용
                )
            }
        }

        return binding.root
    }

    // map_container에 KakaoMapsFragment 추가
    private fun setupMapContainer() {
        kakaoMapsFragment = KakaoMapsFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(binding.mapContainer.id, kakaoMapsFragment).commit()
    }

    override fun onLocationSelected(latitude: Double, longitude: Double, address: String) {
        // 위치 정보 업데이트
        updateMapFragmentLocation(latitude, longitude, address)
    }

    private fun updateMapFragmentLocation(latitude: Double, longitude: Double, address: String) {
        // UI 업데이트 (예: TextView에 표시)
        var address2 = findLatLngFromAddress(latitude, longitude)
        address2 = address2.substring(5)
        //event_add_button 클릭시 주소, 위도, 경도를 AddDetailLocationFragment로 전달
        val bundle = Bundle()
        bundle.putString("address", address2)
        bundle.putDouble("latitude", latitude)
        bundle.putDouble("longitude", longitude)

        val addDetailLocationFragment = AddDetailLocationFragment()
        addDetailLocationFragment.arguments = bundle

        val transaction = (context as MainActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFragment, addDetailLocationFragment).commit()

    }

    private fun findLatLngFromAddress(latitude: Double, longitude: Double): String {
        return try {
            with(context?.let {
                Geocoder(it, Locale.KOREA).getFromLocation(latitude, longitude, 1)
                    ?.first()
            } ?: throw Exception("Geocoder failed")) {
                "${getAddressLine(0)}"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

}

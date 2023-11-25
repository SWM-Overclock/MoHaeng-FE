package com.example.moHaeng.maps

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moHaeng.BuildConfig
import com.example.moHaeng.R
import com.example.moHaeng.databinding.FragmentMapBinding
import com.example.moHaeng.login.JwtInterceptor
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.properties.Delegates

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var apiService: ApiService
    private lateinit var apiService2: ApiService2
    //sharedPreference에서 가져온 locationId
    private var locationId by Delegates.notNull<Long>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = MapView(requireContext())
        apiService = ApiClient.createApiService(requireContext())
        apiService2 = ApiClient2.createApiService(requireContext())



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        locationId = getLocationId(requireContext())
        
        getShopDetail(locationId)
        getShopList()

        return binding.root
    }

    private fun getLocationId(context: Context): Long {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getLong("primaryId", -1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapContainer.addView(mapView)
    }


    interface ApiService {
        @GET("shops/nearby")
        fun getShopList(
        ): Call<List<ShopDetailListResponseDto>>
    }

    interface ApiService2 {
        @GET("location/{locationId}")

        fun getShopDetail(
            @Path("locationId") locationId: Long
        ): Call<LocationDetailResponseDto>
    }

    private fun getShopList() {
        apiService.getShopList().enqueue(object : Callback<List<ShopDetailListResponseDto>> {
            override fun onResponse(
                call: Call<List<ShopDetailListResponseDto>>,
                response: Response<List<ShopDetailListResponseDto>>
            ) {
                if (response.isSuccessful) {
                    handleSuccessfulShopListResponse(response.body())
                } else {
                    Log.e("response", response.toString())
                    handleErrorResponse(response.errorBody()?.string() ?: "Unknown error")
                }
            }

            override fun onFailure(call: Call<List<ShopDetailListResponseDto>>, t: Throwable) {
                handleFailure(t.message ?: "Network error")
            }
        })
    }

    private fun getShopDetail(locationId: Long) {
        apiService2.getShopDetail(locationId).enqueue(object : Callback<LocationDetailResponseDto> {
            override fun onResponse(
                call: Call<LocationDetailResponseDto>,
                response: Response<LocationDetailResponseDto>
            ) {
                if (response.isSuccessful) {
                    handleSuccessfulShopDetailResponse(response.body())
                } else {
                    Log.e("response", response.toString())
                }
            }

            override fun onFailure(call: Call<LocationDetailResponseDto>, t: Throwable) {
                Log.e("responsefail", t.toString())
            }
        })
    }

    private fun handleSuccessfulShopDetailResponse(locationDetail: LocationDetailResponseDto?) {
        if (locationDetail != null) {
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(locationDetail.latitude, locationDetail.longitude), true)

        }
    }


// getLocationList 함수와 동일하게 나머지 부분을 추가합니다.


    private fun handleSuccessfulShopListResponse(newShopList: List<ShopDetailListResponseDto>?) {
        if (newShopList != null) {
            updateMapMarkers(newShopList)
        }
    }

    private fun updateMapMarkers(shopList: List<ShopDetailListResponseDto>) {
        //마커 초기화
        mapView.removeAllPOIItems()
        // shopList를 사용하여 마커 추가
        for (shop in shopList) {
            val marker = MapPOIItem()
            marker.apply {
                itemName = shop.brandCode + shop.name
                mapPoint = MapPoint.mapPointWithGeoCoord(shop.latitude, shop.longitude)
                if (shop.brandCode == "CU") {
                    markerType = MapPOIItem.MarkerType.CustomImage
                    customImageResourceId = R.drawable.cu_marker

                } else if (shop.brandCode == "GS") {
                    markerType = MapPOIItem.MarkerType.CustomImage
                    customImageResourceId = R.drawable.gs_marker
                } else if (shop.brandCode == "7E") {
                    markerType = MapPOIItem.MarkerType.CustomImage
                    customImageResourceId = R.drawable.se_marker
                }
                selectedMarkerType = MapPOIItem.MarkerType.RedPin
            }
            mapView.addPOIItem(marker)
        }
    }

    private fun handleErrorResponse(errorMessage: String) {
        // 서버 응답이 실패한 경우
        showMessage(errorMessage)
    }

    private fun handleFailure(message: String) {
        // 통신 실패
        showMessage(message)
    }

    private fun showMessage(message: String) {
        // 메시지를 화면에 표시하는 로직을 구현하세요
    }


    data class ShopDetailListResponseDto(
        val id: Long,
        val name: String,
        val shopType: ShopType,
        val brandCode: String,
        val latitude: Double,
        val longitude: Double
    )

    data class LocationDetailResponseDto(
        val id: Long,
        val name: String,
        val address: String,
        val latitude: Double,
        val longitude: Double,
        val isPrimary: Boolean
    )

    object ApiClient {
        private const val BASE_URL = "${BuildConfig.SERVER_URL}"

        fun createApiService(context: Context): ApiService {
            val interceptor = JwtInterceptor(context, "shops/nearby")
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

    object ApiClient2 {
        private const val BASE_URL = "${BuildConfig.SERVER_URL}"

        fun createApiService(context: Context): ApiService2 {
            val interceptor = JwtInterceptor(context, "location/{locationId}")
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService2::class.java)
        }
    }
}

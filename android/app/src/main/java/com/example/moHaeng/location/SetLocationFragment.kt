package com.example.moHaeng.location

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moHaeng.BuildConfig
import com.example.moHaeng.MainActivity
import com.example.moHaeng.databinding.FragmentSetLocationBinding
import com.example.moHaeng.login.JwtInterceptor
import com.example.moHaeng.maps.LocationPermissionUtils
import com.example.moHaeng.maps.MapFragment
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class SetLocationFragment : Fragment() {

    private lateinit var binding: FragmentSetLocationBinding
    private lateinit var apiService: ApiService

    private val locationList = mutableListOf<LocationListResponseDto>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetLocationBinding.inflate(inflater, container, false)

        // Retrofit ApiService 초기화
        apiService = ApiClient.createApiService(requireContext())

        setSetLocationRecyclerView()
        setupMapContainer()
        getLocationList()
        setupEditButton()

        return binding.root
    }

    private fun setSetLocationRecyclerView() {
        val recyclerView = binding.locationListRecyclerView
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

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

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
    private fun setupEditButton() {
        binding.editButton.setOnClickListener {
            (activity as MainActivity).setFragment("home", EditLocationFragment())
        }
    }


    private fun getLocationList() {
        apiService.getLocationList().enqueue(object : retrofit2.Callback<List<LocationListResponseDto>> {
            override fun onResponse(call: Call<List<LocationListResponseDto>>, response: retrofit2.Response<List<LocationListResponseDto>>) {
                if (response.isSuccessful) {
                    // 서버 응답이 성공한 경우
                    val newLocationList = response.body()
                    if (newLocationList != null) {
                        // locationList를 사용하여 필요한 작업 수행
                        // 예: RecyclerView에 데이터 설정
                        locationList.clear() // 기존 데이터를 지우고
                        locationList.addAll(newLocationList) // 새로운 데이터로 업데이트
                        // Adapter에 데이터 변경을 알리기
                        (binding.locationListRecyclerView.adapter as LocationAdapter).notifyDataSetChanged()
                    }
                } else {
                    // 서버 응답이 실패한 경우
                    val errorMessage = response.errorBody()?.string()
                    showMessage(errorMessage ?: "Unknown error")
                }
            }

            override fun onFailure(call: Call<List<LocationListResponseDto>>, t: Throwable) {
                // 통신 실패
                showMessage(t.message ?: "Network error")
            }
        })
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    interface ApiService {
        @GET("location/list")
        fun getLocationList(): Call<List<LocationListResponseDto>>
    }

    object ApiClient {
        private const val BASE_URL = "${BuildConfig.SERVER_URL}"

        fun createApiService(context: Context): ApiService {
            val interceptor = JwtInterceptor(context, "location/list")
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

    data class LocationListResponseDto(
        val id: Long,
        val name: String,
        val address: String,
        val primary: Boolean
    )
}

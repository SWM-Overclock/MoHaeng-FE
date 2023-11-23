package com.example.moHaeng.location

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.BuildConfig
import com.example.moHaeng.MainActivity
import com.example.moHaeng.databinding.FragmentEditLocationBinding
import com.example.moHaeng.login.JwtInterceptor
import com.example.moHaeng.maps.LocationPermissionUtils.checkLocationPermission
import com.example.moHaeng.maps.LocationPermissionUtils.isLocationServiceEnabled
import com.example.moHaeng.maps.LocationPermissionUtils.requestLocationPermission
import com.example.moHaeng.maps.MapFragment
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

class EditLocationFragment : Fragment() {

    private lateinit var binding: FragmentEditLocationBinding
    private lateinit var locationList: MutableList<SetLocationFragment.LocationListResponseDto>
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditLocationBinding.inflate(inflater, container, false)
        locationList = mutableListOf()

        apiService = ApiClient.createApiService(requireContext())
8
        setEditLocationRecyclerView()
        setupMapContainer()
        getLocationList()

        return binding.root
    }

    private fun setEditLocationRecyclerView() {
        val recyclerView: RecyclerView = binding.locationListRecyclerView
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        val adapter = EditAdapter(locationList, object : EditAdapter.OnDeleteClickListener {
            override fun onDeleteClick(locationId: Long) {
                // 삭제 이벤트 처리
                deleteLocation(locationId)
            }
        })
        recyclerView.adapter = adapter
    }

    private fun setupMapContainer() {
        binding.locationSetView.setOnClickListener {
            // 위치 권한 확인
            if (checkLocationPermission(requireContext())) {
                // 위치 권한이 있는 경우
                if (isLocationServiceEnabled(requireContext())) {
                    // 위치 서비스가 활성화된 경우, 지도 설정
                    (activity as MainActivity).setFragment("home", MapFragment())
                } else {
                    // 위치 서비스가 비활성화된 경우, 사용자에게 메시지 표시
                    Toast.makeText(requireContext(), "위치 서비스가 비활성화되어 있습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // 위치 권한이 없는 경우, 권한 요청
                requestLocationPermission(this, LOCATION_PERMISSION_REQUEST_CODE)
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    private fun getLocationList() {
        // 서버에서 데이터를 가져오는 API 호출
        SetLocationFragment.ApiClient.createApiService(requireContext())
            .getLocationList().enqueue(object : retrofit2.Callback<List<SetLocationFragment.LocationListResponseDto>> {
                override fun onResponse(
                    call: retrofit2.Call<List<SetLocationFragment.LocationListResponseDto>>,
                    response: retrofit2.Response<List<SetLocationFragment.LocationListResponseDto>>
                ) {
                    if (response.isSuccessful) {
                        val newLocationList = response.body()
                        if (newLocationList != null) {
                            locationList.clear()
                            locationList.addAll(newLocationList)
                            // Adapter에 데이터 변경을 알리기
                            (binding.locationListRecyclerView.adapter as EditAdapter).notifyDataSetChanged()
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        showMessage(errorMessage ?: "Unknown error")
                    }
                }

                override fun onFailure(call: retrofit2.Call<List<SetLocationFragment.LocationListResponseDto>>, t: Throwable) {
                    showMessage(t.message ?: "Network error")
                }
            })
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun deleteLocation(locationId: Long) {
        Log.e("locationId", locationId.toString())
        apiService.deleteLocation(locationId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // 성공적으로 업데이트된 경우
                    showMessage("삭제 완료되었습니다.")
                    // 적절한 처리 수행
                } else {
                    // 서버 응답이 실패한 경우
                    val errorMessage = response.errorBody()?.string()
                    showMessage("다시 시도해주세요")
                    Log.e("error", errorMessage.toString())

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // 통신 실패
                showMessage( "다시 시도해주세요")
            }
        })
    }

    interface ApiService {
        @DELETE("location/{locationId}")
        fun deleteLocation(
            @Path("locationId") locationId: Long,
        ): Call<Void>
    }

    object ApiClient {
        private const val BASE_URL = "${BuildConfig.SERVER_URL}"

        fun createApiService(context: Context): EditLocationFragment.ApiService {
            val interceptor = JwtInterceptor(context, "location/{locationId}")
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(EditLocationFragment.ApiService::class.java)
        }
    }
}
package com.example.moHaeng.location

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moHaeng.BuildConfig
import com.example.moHaeng.MainActivity
import com.example.moHaeng.databinding.FragmentSetLocationBinding
import com.example.moHaeng.home.HomeFragment
import com.example.moHaeng.login.JwtInterceptor
import com.example.moHaeng.login.LoginActivity
import com.example.moHaeng.maps.LocationPermissionUtils
import com.example.moHaeng.maps.FindMapFragment
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

class SetLocationFragment : Fragment() {

    private lateinit var binding: FragmentSetLocationBinding
    private lateinit var apiService: ApiService
    private lateinit var apiService2: ApiService2
    private val viewModel: LocationViewModel by activityViewModels()

    private val locationList = mutableListOf<LocationListResponseDto>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetLocationBinding.inflate(inflater, container, false)

        // Retrofit ApiService 초기화
        apiService = ApiClient.createApiService(requireContext())
        apiService2 = ApiClient2.createApiService2(requireContext())

        setSetLocationRecyclerView()
        setupMapContainer()
        getLocationList()
        setupEditButton()

        viewModel.locationList.observe(viewLifecycleOwner) { locationList ->
            updateLocationRecyclerView(locationList)
        }

        return binding.root
    }

    private fun setSetLocationRecyclerView() {
        val recyclerView = binding.locationListRecyclerView
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        val adapter = LocationAdapter(locationList) { locationId ->
            showAlertDialog(locationId, locationList.find { it.id == locationId }!!.name)
        }

        adapter.locationList.sortByDescending { it.isPrimary }

        recyclerView.adapter = adapter
    }

    private fun setupMapContainer() {
        binding.locationSetView.setOnClickListener {
            // 위치 권한 확인
            if (LocationPermissionUtils.checkLocationPermission(requireContext())) {
                // 위치 권한이 있는 경우
                if (LocationPermissionUtils.isLocationServiceEnabled(requireContext())) {
                    // 위치 서비스가 활성화된 경우, 지도 설정
                    (activity as MainActivity).setFragment("home", FindMapFragment())
                } else {
                    // 위치 서비스가 비활성화된 경우, 사용자에게 메시지 표시
                    Toast.makeText(requireContext(), "위치 서비스가 비활성화되어 있습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // 위치 권한이 없는 경우, 권한 요청
                LocationPermissionUtils.requestLocationPermission(
                    this,
                    LOCATION_PERMISSION_REQUEST_CODE
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

    private fun updateLocationRecyclerView(newLocationList: List<LocationListResponseDto>) {
        // LocationAdapter 데이터 변경을 알리기
        (binding.locationListRecyclerView.adapter as LocationAdapter).apply {
            locationList.clear()
            locationList.addAll(newLocationList)
            notifyDataSetChanged()
        }
    }


    private fun getLocationList() {
        apiService.getLocationList().enqueue(object : Callback<List<LocationListResponseDto>> {
            override fun onResponse(call: Call<List<LocationListResponseDto>>, response: Response<List<LocationListResponseDto>>) {
                if (response.isSuccessful) {
                    // 서버 응답이 성공한 경우
                    val newLocationList = response.body()
                    if (newLocationList != null) {
                        // locationList 사용하여 필요한 작업 수행
                        // 예: RecyclerView 데이터 설정
                        locationList.clear() // 기존 데이터를 지우고
                        locationList.addAll(newLocationList) // 새로운 데이터로 업데이트
                        // Adapter 데이터 변경을 알리기
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

    interface ApiService : LoginActivity.ApiService {
        @GET("location/list")
        fun getLocationList(): Call<List<LocationListResponseDto>>
    }

    interface ApiService2 : LoginActivity.ApiService {

        @PUT("location/primary/{locationId}")
        fun setPrimaryLocation(
            @Path("locationId") locationId: Long,
        ): Call<Void>
    }

    private fun savePrimaryLocation(context: Context, primaryLocation: String) {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("primaryLocation", primaryLocation)
        editor.apply()
    }

    private fun showAlertDialog(locationId: Long, locationName: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        alertDialogBuilder.setTitle("알림")
        alertDialogBuilder.setMessage("검색 위치로 지정하시겠습니까?")

        alertDialogBuilder.setPositiveButton("확인") { dialog, _ ->
            setPrimaryLocation(locationId)
            savePrimaryLocation(requireContext(), locationName)
            showToast("선택 위치가 변경되었습니다.")
            dialog.dismiss()
        }

        alertDialogBuilder.setNegativeButton("취소") { dialog, _ ->
            // 사용자가 취소 버튼을 눌렀을 때의 동작
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(message: String) {
        // Toast 메시지 생성 및 표시
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setPrimaryLocation(locationId: Long) {
        apiService2.setPrimaryLocation(locationId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // 성공적으로 업데이트된 경우
                    showMessage("현재 주소 설정이 완료되었습니다.")
                    getLocationList()

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

    object ApiClient2 {
        private const val BASE_URL = "${BuildConfig.SERVER_URL}"

        fun createApiService2(context: Context): ApiService2 {
            val interceptor = JwtInterceptor(context, "location/primary/{locationId}")
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

    // recyclerview가 clikc되면 서버로 해당 location의 id를 보내서 해당 location을 primary로 설정


    data class LocationListResponseDto(
        val id: Long,
        val name: String,
        val address: String,
        var isPrimary: Boolean
    )
}

package com.example.moHaeng.maps

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moHaeng.BuildConfig
import com.example.moHaeng.MainActivity
import com.example.moHaeng.databinding.FragmentAddDetailLocationBinding
import com.example.moHaeng.location.EditLocationFragment
import com.example.moHaeng.login.JwtInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class AddDetailLocationFragment : Fragment() {

    private lateinit var binding: FragmentAddDetailLocationBinding
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var address: String = "11"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDetailLocationBinding.inflate(inflater, container, false)

        // 아규먼트에서 데이터를 가져옵니다.
        latitude = arguments?.getDouble("latitude", 0.0) ?: 0.0
        longitude = arguments?.getDouble("longitude", 0.0) ?: 0.0
        address = arguments?.getString("address", "") ?: ""

        // 가져온 주소를 UI에 설정합니다.
        setAddress(address)

        // add_event_button 클릭 이벤트 처리
        binding.eventAddButton.setOnClickListener {
            // 여기에서 서버로 데이터를 보내는 작업을 수행합니다.
            sendDataToServer()
            (activity as MainActivity).setFragment("home", EditLocationFragment())

        }

        return binding.root
    }

    private fun sendDataToServer() {
        val apiService = ApiClient.createApiService(requireContext(), "create")

        // 여기에서 필요한 데이터를 담아서 서버로 전송합니다.
        val location = LocationData(binding.addEventEnter.text.toString(), address, latitude, longitude)
        Log.e("location", location.toString())

        apiService.createLocation(location).enqueue(object : retrofit2.Callback<LocationCreateResponseDto> {
            override fun onResponse(call: Call<LocationCreateResponseDto>, response: retrofit2.Response<LocationCreateResponseDto>) {
                if (response.isSuccessful) {
                    // 등록 성공
                    showMessage("위치 등록이 완료되었습니다!")
                } else {
                    showMessage("등록에 실패하였습니다. 다시 시도해주세요")
                }
            }

            override fun onFailure(call: Call<LocationCreateResponseDto>, t: Throwable) {
                // 통신 실패
                showMessage("다시 시도해주세요")
                Log.e("API Error", "API request failed", t)
            }
        })
    }

    // UI에서 메시지를 표시하는 함수
    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    // UI에서 주소를 설정하는 함수
    private fun setAddress(address: String) {
        binding.addressTitle.text = address
    }

    // 데이터 클래스로 변경
    data class LocationData(
        val name: String,
        val address: String,
        val latitude: Double,
        val longitude: Double
    )

    data class LocationCreateResponseDto(
        val id: Long,
        val name: String,
        val address: String,
        val latitude: Double,
        val longitude: Double,
        val primary: Boolean
    )

    object ApiClient {
        private val BASE_URL = BuildConfig.SERVER_URL

        // ApiService 인스턴스 생성 함수
        fun createApiService(context: Context, apiEndpoint: String): ApiService {
            val interceptor = JwtInterceptor(context, apiEndpoint)
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

    // 서버 API 정의 인터페이스
    interface ApiService {
        @POST("location/create")
        fun createLocation(@Body location: LocationData): Call<LocationCreateResponseDto>
    }
}

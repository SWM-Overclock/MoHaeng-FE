package com.example.moHaeng.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.moHaeng.BuildConfig
import com.example.moHaeng.MainActivity
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


public class JwtCheck {
    //sharedpreference에 저장된 jwt를 가져오는 함수
    fun getAccessToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("accessToken", null)
    }

    fun getRefreshToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("refreshToken", null)
    }


    //토큰 확인 실패시 로그인 화면으로 가는 함수
    fun goToLoginActivity(nowActivity: Activity) {
        val intent = Intent(nowActivity, LoginActivity::class.java)
        nowActivity.startActivity(intent)
    }

    //토큰 확인 성공시 메인 화면으로 가는 함수
    fun goToMainActivity(nowActivity: Activity) {
        val intent = Intent(nowActivity, MainActivity::class.java)
        nowActivity.startActivity(intent)
    }


    fun saveJwtToken(context: Context, accessToken: String, refreshToken: String) {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("refreshToken", refreshToken)
        editor.putString("accessToken", accessToken)
        editor.apply()
    }

    //jwttoken을 sharedpreference에서 제거하는 함수
    fun removeJwtToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("refreshToken")
        editor.remove("accessToken")
        editor.apply()
    }

    fun refreshAccessToken(context: Context): String? {
        val refreshToken = getRefreshToken(context)
        val BASE_URL = BuildConfig.SERVER_URL

        return refreshToken?.let {
            // Retrofit 인스턴스 생성
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // JwtApi 서비스 생성
            val jwtService = retrofit.create(JwtApi::class.java)

            // 액세스 토큰 갱신 엔드포인트 호출
            val call = jwtService.refreshToken(RefreshAccessRequest(refreshToken))
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    saveJwtToken(context, response.body()?.accessToken ?: "", refreshToken)
                    return response.body()?.accessToken
                }
            } catch (e: Exception) {
                // 예외 처리, 예를 들어 네트워크 문제
                // Toast.makeText(context, "액세스 토큰 갱신에 실패했습니다", Toast.LENGTH_SHORT).show()
            }

            null
        }
    }

    interface JwtApi {
        @POST("/auth/token-reissue") // 갱신 토큰 엔드포인트에 맞게 수정
        fun refreshToken(@Body token: RefreshAccessRequest): Call<JwtResponse>
    }

    data class RefreshAccessRequest(
        val refreshToken: String
    )

    data class JwtResponse (
        val accessToken: String
    )
}


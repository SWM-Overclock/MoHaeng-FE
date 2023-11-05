package com.example.moHaeng.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class JwtCheck {
    //sharedpreference에 저장된 jwt를 가져오는 함수
    fun getAccessToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("accessToken", null)
    }

//    fun checkJwtFromServer{
//        //retrofit2를 통해 refresh token을 서버로 보내서 유효한지 확인한다.
//        //유효하면 true, 아니면 false를 반환한다.
//        val token = "Bearer " + getJwtToken(this) // 토큰을 가져온 후 "Bearer "를 추가하여 헤더 형식으로 만듭니다
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://your-api-url.com/") // 실제 API URL로 변경
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val apiService = retrofit.create(LoginActivity.ApiService::class.java)
//
//        apiService.validateToken(token).enqueue(object : Callback<Void> {
//            override fun onResponse(call: Call<Void>, response: Response<Void>): Boolean {
//                return response.isSuccessful
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//                // 네트워크 오류 등으로 요청이 실패한 경우
//                // 에러 처리 로직 추가
//            }
//        })
//
//
//    }


    //토큰 확인 실패시 로그인 화면으로 가는 함수
    fun goToLoginActivity(nowActivity: Activity) {
        val intent = Intent(nowActivity, LoginActivity::class.java)
        nowActivity.startActivity(intent)
    }




//    //jwt를 서버로 보내서 유효한지 확인하는 함수
//    fun checkJwt(jwt: String): Boolean {
//        return false
//    }
//    //jwt를 가져오는 함수
//
//    //jwt를 가져오는데 실패하면 null을 반환한다.
//    //jwt를 가져오는데 성공하면 jwt를 반환한다.
//
//
//    //jwt를 서버로 보내서 유효한지 확인한다.
//    //유효하면 true, 아니면 false를 반환한다.

}
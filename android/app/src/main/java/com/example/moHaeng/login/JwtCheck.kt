package com.example.moHaeng.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.moHaeng.BuildConfig
import com.example.moHaeng.MainActivity
import com.example.moHaeng.login.LoginActivity.*
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

}
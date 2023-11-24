package com.example.moHaeng.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moHaeng.MainActivity
import com.example.moHaeng.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //최소 1초간 스플래시 화면을 보여주며 로그인 여부를 확인한다.
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            checkLogin()
        }, 1000)
    }

    private fun checkLogin() {
        val jwtAccessToken = JwtCheck().getAccessToken(this)

        if (jwtAccessToken != null) {
            JwtCheck().goToMainActivity(this)
        }
        else {
            val jwtRefreshToken = JwtCheck().getRefreshToken(this)
            if (jwtRefreshToken != null) {
                JwtCheck().refreshAccessToken(this)
                JwtCheck().goToMainActivity(this)
            }
            else {
                JwtCheck().goToLoginActivity(this)
            }
        }
    }
}
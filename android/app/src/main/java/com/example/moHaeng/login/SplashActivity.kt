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



        val jwtAccessToken = JwtCheck().getAccessToken(this)


        //1초간
        if (jwtAccessToken != null) {
            JwtCheck().goToMainActivity(this)
        }
        else {
            val jwtRefreshToken = JwtCheck().getRefreshToken(this)
            if (jwtRefreshToken != null) {
                JwtCheck().goToMainActivity(this)
                //todo 서버로 jwtRefreshToken을 보내서 jwtAccessToken을 받아온다.
            }
            else {
                JwtCheck().goToLoginActivity(this)
            }
        }


    }
}
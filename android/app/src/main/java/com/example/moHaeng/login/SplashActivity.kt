package com.example.moHaeng.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.moHaeng.MainActivity
import com.example.moHaeng.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        val jwtToken = JwtCheck().getAccessToken(this)
//        if (jwtToken != null) {
//            JwtCheck().goToLoginActivity(this)
//        }



        //JwtCheck의 getJwtToken 함수를 호출하여 jwt를 가져온다.

        //val jwt = JwtCheck().
        //jwt가 null이 아니면 jwt를 서버로 보내서 유효한지 확인한다.
        //유효하면 MainActivity로 이동한다.



        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },3000)
    }
}
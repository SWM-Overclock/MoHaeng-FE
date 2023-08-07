package com.example.moHaeng

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moHaeng.databinding.ActivityMainBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    // 통신할 ApiClient를 정의하는 객체
    object ApiClient {
        private const val BASE_URL = "https://your.server.url/"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getClient(): Retrofit {
            return retrofit
        }
    }

    data class LoginResponse(
        val jwtToken: String
    )

    data class AccessTokenRequest(
        val access_token: String
    )


    interface ApiService {

        // 기존에 정의한 메소드들과 함께, 서버로 Access Token을 보내는 메소드를 추가
        @POST("login") // 해당 엔드포인트는 실제 서버에 맞게 변경해야 합니다.
        fun sendAccessTokenToServer(@Body accessTokenRequest: AccessTokenRequest): Call<LoginResponse>
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        /** KakoSDK init */
        KakaoSdk.init(this, this.getString(R.string.kakao_app_key))

        /** Click_listener */
        binding.btnStartKakaoLogin.setOnClickListener {
            kakaoLogin() //로그인
        }
        binding.btnStartKakaoLogout.setOnClickListener {
            kakaoLogout() //로그아웃
        }
        binding.btnStartKakaoUnlink.setOnClickListener {
            kakaoUnlink() //연결해제
        }
        binding.btnLocationPermission.setOnClickListener {
            btn_location_permission() //연결해제
        }
    }

    private fun kakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                TextMsg(this, "카카오계정으로 로그인 실패 : ${error}")
                setLogin(false)
            } else if (token != null) {
                // Access Token을 서버로 보내는 메소드 호출
                sendAccessTokenToServer(token.accessToken)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    TextMsg(this, "카카오톡으로 로그인 실패 : ${error}")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    TextMsg(this, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    Log.e(ContentValues.TAG, "엑세스 토큰: ${token.accessToken}")
                    Log.e(ContentValues.TAG, "엑세스 토큰: ${KakaoSdk.redirectUri}")

                    // Access Token을 서버로 보내는 메소드 호출
                    sendAccessTokenToServer(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }


    private fun kakaoLogout(){
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                TextMsg(this, "로그아웃 실패. SDK에서 토큰 삭제됨: ${error}")
            }
            else {
                TextMsg(this, "로그아웃 성공. SDK에서 토큰 삭제됨")
                setLogin(false)
            }
        }
    }

    private fun kakaoUnlink(){
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                TextMsg(this, "연결 끊기 실패: ${error}")
            }
            else {
                TextMsg(this, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                setLogin(false)
            }
        }
    }

    private fun TextMsg(act: Activity, msg : String){
        binding.tvHashKey.text = msg
    }

    private fun setLogin(bool: Boolean){
        binding.btnStartKakaoLogin.visibility = if(bool) View.GONE else View.VISIBLE
        binding.btnStartKakaoLogout.visibility = if(bool) View.VISIBLE else View.GONE
        binding.btnStartKakaoUnlink.visibility = if(bool) View.VISIBLE else View.GONE
    }

    private fun sendAccessTokenToServer(accessToken: String) {
        // Retrofit 인스턴스 생성
        val apiService = ApiClient.getClient().create(ApiService::class.java)

        // Access Token을 서버로 보내기 위한 데이터 클래스 인스턴스 생성
        val request = AccessTokenRequest(accessToken)

        // Access Token을 서버로 보내는 요청
        apiService.sendAccessTokenToServer(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // 서버로부터 응답을 받아 처리하는 로직
                    val loginResponse = response.body()
                    // 예시) 로그인 성공 여부 등을 확인하고 처리
                } else {
                    // 서버 요청은 성공했지만 서버에서 오류 응답을 준 경우 처리
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                val context = this@MainActivity
                Toast.makeText(context, "서버와 통신 실패 ㅠㅠ", Toast.LENGTH_SHORT).show()
            }
        })

        setLogin(true)
    }





    //    btn_location_permission을 클릭하면 LocationPermissionActivity로 이동
    fun btn_location_permission() {
        val intent = Intent(this, LocationPermissionActivity::class.java)
        startActivity(intent)
    }
}
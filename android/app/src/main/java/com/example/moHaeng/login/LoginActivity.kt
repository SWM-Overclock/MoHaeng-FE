package com.example.moHaeng.login

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moHaeng.BuildConfig
import com.example.moHaeng.R
import com.example.moHaeng.databinding.ActivityLoginBinding
import com.google.gson.annotations.SerializedName
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

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    // 통신할 ApiClient를 정의하는 객체
    object ApiClient {
        private const val BASE_URL = BuildConfig.OAUTH_SERVER

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
        val id: Long,
        val nickName: String,
        val email: String,
        val imageUrl: String,
        val tokenType: String,
        val accessToken: String,
        val refreshToken: String
    )

    data class AccessTokenRequest(
        @SerializedName("token")
        val token: String
    )


    interface ApiService {

        // 기존에 정의한 메소드들과 함께, 서버로 Access Token을 보내는 메소드를 추가
        @POST("kakao")
        fun sendAccessTokenToServer(@Body token: AccessTokenRequest): Call<LoginResponse>

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        /** KakoSDK init */
        KakaoSdk.init(this, this.getString(R.string.kakao_app_key))

        /** Click_listener */
        binding.btnStartKakaoLogin.setOnClickListener {
            kakaoLogin() //로그인
        }
    }

    private fun kakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Toast.makeText(this, "카카오 계정으로 로그인 실패", Toast.LENGTH_SHORT).show()
            } else if (token != null) {
                // Access Token을 서버로 보내는 메소드 호출
                sendAccessTokenToServer(token.accessToken)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Toast.makeText(this, "카카오톡으로 로그인 실패", Toast.LENGTH_SHORT).show()

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.e(ContentValues.TAG, "엑세스 토큰: ${token.accessToken}")

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
                Toast.makeText(this, "로그아웃 실패, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                JwtCheck().removeJwtToken(this)
                Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun kakaoUnlink(){
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Toast.makeText(this, "연결 끊기 실패, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                JwtCheck().removeJwtToken(this)
                Toast.makeText(this, "카카오톡 연결 끊기 성공", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun sendAccessTokenToServer(accessToken: String) {
        // Retrofit 인스턴스 생성
        val apiService = ApiClient.getClient().create(ApiService::class.java)

        // Access Token을 서버로 보내기 위한 데이터 클래스 인스턴스 생성
        val request = AccessTokenRequest(accessToken)

        // Access Token을 서버로 보내는 요청
        apiService.sendAccessTokenToServer(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val context = this@LoginActivity

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        JwtCheck().saveJwtToken(context, loginResponse.accessToken, loginResponse.refreshToken)
                        JwtCheck().goToMainActivity(context)
                    }
                } else {
                    Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                val context = this@LoginActivity
                Toast.makeText(context, "서버와 통신 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // sharedpreference에 jwt 토큰을 저장하는 함수
}
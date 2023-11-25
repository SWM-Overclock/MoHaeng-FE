package com.example.moHaeng.login

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response


class JwtInterceptor(private val context: Context, private val apiEndpoint: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentAccessToken = JwtCheck().getAccessToken(context)

        val request = chain.request().newBuilder()
            .header("Authorization","Bearer $currentAccessToken")
            .build()

        val response = chain.proceed(request)

        if (response.code == 500) {
            // 응답이 401 Unauthorized일 경우, 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급 시도
            val newAccessToken = JwtCheck().refreshAccessToken(context)
            if (newAccessToken != null) {
                // 새로운 액세스 토큰으로 다시 요청 보내기
                JwtCheck().saveJwtToken(context, newAccessToken, JwtCheck().getRefreshToken(context)!!)
                val newRequest = request.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()
                return chain.proceed(newRequest)
            }
        }

        return response
    }
}


//예시
//object ApiClient {
//    private val BASE_URL = "your_base_url_here"
//
//    fun createApiService(context: Context, apiEndpoint: String): ApiService {
//        val interceptor = JwtInterceptor(context, apiEndpoint)
//        val client: OkHttpClient = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()
//
//        val retrofit: Retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//
//        return retrofit.create(ApiService::class.java)
//    }
//}
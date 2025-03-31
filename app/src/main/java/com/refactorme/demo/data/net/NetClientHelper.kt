package com.refactorme.demo.data.net

import com.refactorme.demo.BuildConfig
import com.refactorme.demo.data.net.NetConstants.BASE_API_PATH
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetClientHelper {
    private const val CALL_TIMEOUT = 180L   // 3 min
    private const val CONNECT_TIMEOUT = 60L // 1 min

    inline fun <reified T> getApiService(): T {
        val retrofit = createRetrofit(baseUrl = BASE_API_PATH)
        return retrofit.create(T::class.java)
    }

    fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            if (logEnabled) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(interceptor)
            }
        }.build()
    }

    private val logEnabled: Boolean = BuildConfig.DEBUG
}
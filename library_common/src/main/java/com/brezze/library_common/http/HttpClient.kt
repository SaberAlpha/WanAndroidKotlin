package com.brezze.library_common.http

import android.util.Log
import com.brezze.library_common.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpClient private constructor() {

    companion object {

        private val instance by lazy { HttpClient() }

        val api: HttpApi by lazy { HttpClient.instance.buildApi() }

        val api2: HttpApi by lazy { HttpClient.instance.buildApi2() }
    }

    private fun buildApi() = Retrofit.Builder()
        .baseUrl(HTTP_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient())
        .build()
        .create(HttpApi::class.java)

    private fun buildApi2() = Retrofit.Builder()
        .baseUrl(HTTP_VERSION_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient())
        .build()
        .create(HttpApi::class.java)

    private fun okHttpClient() = OkHttpClient.Builder()
        .connectTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor())
        .build()

    private fun loggingInterceptor() =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            if (message.startsWith("{") && message.endsWith("}") ||
                message.startsWith("[") && message.endsWith("]")
            ) {
                Log.d(HTTP_LOG_TAG, Gson().toJson(message))
            } else {
                Log.d(HTTP_LOG_TAG, message)
            }
        }).apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

}
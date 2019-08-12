package com.karashok.common.net

import com.karashok.common.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name RetrofitU
 * @date 2019/07/16 21:52
 **/
object RetrofitU {

    val mRetrofit: Retrofit

    init {
        val builder = OkHttpClient.Builder()
        builder.apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            })
            addInterceptor(ReadCookiesInterceptor())
            addInterceptor(SaveCookiesInterceptor())
        }
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(UrlPath.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
        mRetrofit = retrofitBuilder.build()
    }

}
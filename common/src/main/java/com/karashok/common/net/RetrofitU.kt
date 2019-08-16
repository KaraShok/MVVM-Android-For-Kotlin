package com.karashok.common.net

import com.karashok.common.BuildConfig
import com.karashok.common.utils.UserInfoHelper
import okhttp3.*
import okhttp3.Headers.Companion.toHeaders
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name RetrofitU
 * @date 2019/07/16 21:52
 **/
object RetrofitU {

    var mRetrofit: Retrofit by Delegates.notNull()
    private val mHeaders: HashMap<String,String> = HashMap()

    init {
        mRetrofit = getRetrofitClient()
    }

    private fun getRetrofitClient(): Retrofit{
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(UrlPath.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
        return retrofitBuilder.build()
    }

    private fun getOkHttpClient(): OkHttpClient{
        val builder = OkHttpClient.Builder()
        builder.apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(getHttpLoInterceptor())
            addInterceptor(ReadCookiesInterceptor())
            addInterceptor(SaveCookiesInterceptor())
            addInterceptor(SaveCookiesInterceptor())
            addInterceptor(getHeaderInterceptor(mHeaders))
        }
        return builder.build()
    }

    private fun getHeaderInterceptor(headers: HashMap<String,String>): Interceptor{
        val interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val newBuilder = chain.request().newBuilder()
                newBuilder.headers(headers.toHeaders())
                return chain.proceed(newBuilder.build())
            }
        }
        return interceptor
    }

    private fun getHttpLoInterceptor():  HttpLoggingInterceptor{
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

}
package com.karashok.common.net

import com.karashok.common.utils.UserInfoHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION  读取cookie并设置
 * @name ReadCookiesInterceptor
 * @date 2019/08/03 11:44
 **/
class ReadCookiesInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val preferences = UserInfoHelper.getCookie()
        preferences?.let {
            for (cookie in preferences) {
                builder.addHeader("Cookie", cookie)
            }
        }
        return chain.proceed(builder.build())
    }
}
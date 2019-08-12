package com.karashok.common.net

import com.karashok.common.utils.UserInfoHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.HashSet

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION 保存Cookie
 * @name SaveCookiesInterceptor
 * @date 2019/08/03 11:44
 **/
class SaveCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies = HashSet<String>()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            UserInfoHelper.saveCookie(cookies)
        }
        return originalResponse
    }
}
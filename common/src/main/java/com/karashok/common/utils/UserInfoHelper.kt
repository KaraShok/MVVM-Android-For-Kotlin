package com.karashok.common.utils

import android.content.Context
import com.karashok.common.data.UserResponse

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name UserInfoHelper
 * @date 2019/08/03 11:45
 **/
object UserInfoHelper {

    private var ctx: Context? = null

    fun init(context: Context) {
        ctx = context.applicationContext
    }

    /**
     * 保存用户信息
     */
    fun saveUser(user: UserResponse) {
        val sp = ctx?.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        sp?.edit()?.apply {
            putString("email", user.email)
            putString("icon", user.icon)
            putString("username", user.username)
            putString("password", user.password)
            putString("token", user.token)
            putInt("id", user.id)
            putInt("type", user.type)
        }?.apply()
    }

    /**
     * 获取保存的用户信息
     */
    fun getUser(): UserResponse? {
        val sp = ctx?.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val user = UserResponse(
            sp?.getString("email", "") ?: "",
            sp?.getString("icon", "") ?: "",
            sp?.getInt("id", -1) ?: -1,
            sp?.getString("password", "") ?: "",
            sp?.getString("token", "") ?: "",
            sp?.getInt("type", -1) ?: -1,
            sp?.getString("username", "") ?: ""

        )
        return if (user.id == -1) null else user
    }

    /**
     * 清除保存的用户信息
     */
    fun clearUser() {
        val sp = ctx?.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        sp?.edit()?.clear()?.apply()
    }


    fun saveCookie(set:HashSet<String>){
        val sp = ctx?.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        sp?.edit()?.apply {
            putStringSet("cookie",set)
        }?.apply()
    }

    fun getCookie():HashSet<String>?{
        val sp = ctx?.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        return sp?.getStringSet("cookie", hashSetOf<String>()) as HashSet<String>?
    }

}
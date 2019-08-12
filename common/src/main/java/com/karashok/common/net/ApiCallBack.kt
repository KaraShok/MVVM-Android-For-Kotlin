package com.karashok.common.net

import com.karashok.common.data.BaseResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ApiCallBack
 * @date 2019/08/03 14:41
 **/
class ApiCallBack<T>(val result: BaseResult<T>.() -> Unit) : Callback<BaseResult<T>> {

    override fun onResponse(call: Call<BaseResult<T>>, response: Response<BaseResult<T>>) {
        val code = response.code()
        if (code in 200..299) {
            val errorCode = response.body()?.errorCode
            if (errorCode == -1001) {    //需要重新登录
//                App.instance.user = null
            } else {
                response.body()!!.result()
            }
        } else {
            onFailure(call, RuntimeException("response error,detail = " + response.raw().toString()))
        }
    }

    override fun onFailure(call: Call<BaseResult<T>>, throwable: Throwable) {
        val error = when (throwable) {
            is SocketTimeoutException -> "网络不给力！"
            is ConnectException -> "当前的网络不通！"
            is UnknownHostException -> "当前的网络不通！"
            else -> "当前服务异常！"
            //可进一步细分错误类型
        }
        BaseResult<T>().apply {
            errorCode = -1
            errorMsg = error
        }.result()
    }
}
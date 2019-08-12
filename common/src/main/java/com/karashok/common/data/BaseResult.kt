package com.karashok.common.data

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name BaseResult
 * @date 2019/08/03 14:22
 **/
class BaseResult<T> {
    var data: T? = null
    var errorCode: Int = -1
    var errorMsg: String = ""

    val isSuccess: Boolean
        get() = errorCode == 0
}
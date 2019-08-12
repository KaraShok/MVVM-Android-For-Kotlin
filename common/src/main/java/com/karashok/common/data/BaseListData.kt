package com.karashok.common.data

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name BaseListData
 * @date 2019/08/03 14:23
 **/
class BaseListData<T> {
    val curPage: Int = 0
    val datas: MutableList<T> = mutableListOf()
    val offset: Int = 0
    val over: Boolean = true
    val pageCount: Int = 0
    val size: Int = 0
    val total: Int = 0
}
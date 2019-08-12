package com.karashok.lib_base.widget.states_view

import android.os.Bundle

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION 页面状态-回调
 * @name LayoutStatesChangeCallBack
 * @date 2019/07/31 15:17
 **/
interface LayoutStatesChangeCallBack {

    companion object{
        val STATE_TYPE_LOADING = 10
        val STATE_TYPE_FINISH = 11
        val STATE_TYPE_ERROR = 12
        val STATE_TYPE_NULL_DATA = 13
        val STATE_TYPE_CONTENT = 14
    }

    /**
     * 页面状态 - 加载
     */
    fun onStatesLoading(bundle: Bundle?, registerEvent: Boolean)

    /**
     * 页面状态 - 结束
     */
    fun onStatesFinish(bundle: Bundle?, registerEvent: Boolean)

    /**
     * 页面状态 - 失败
     */
    fun onStatesError(bundle: Bundle?, registerEvent: Boolean)

    /**
     * 页面状态 - 空数据
     */
    fun onStatesNullData(bundle: Bundle?, registerEvent: Boolean)

    /**
     * 页面显示
     */
    fun onContentView()
}
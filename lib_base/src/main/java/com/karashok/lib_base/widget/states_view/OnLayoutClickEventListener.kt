package com.karashok.lib_base.widget.states_view

import android.os.Bundle

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION 状态View的点击事件回调
 * @name OnLayoutClickEventListener
 * @date 2019/07/31 15:20
 **/
interface OnLayoutClickEventListener {

    companion object{
        /**
         * 加载中View的点击事件回调标识
         */
        val LAYOUT_EVENT_CODE_LOADING = 3 shl 3

        /**
         * 结束View的点击事件回调标识
         */
        val LAYOUT_EVENT_CODE_FINISH = 3 shl 4

        /**
         * 失败View的点击事件回调标识
         */
        val LAYOUT_EVENT_CODE_ERROR = 3 shl 5

        /**
         * 空数据View的点击事件回调标识
         */
        val LAYOUT_EVENT_CODE_NULL_DATA = 3 shl 6
    }

    /**
     * 状态View的点击事件回调
     * @param eventCode 标识
     * @param bundle 数据
     */
    fun layoutClickEvent(eventCode: Int, bundle: Bundle?)
}
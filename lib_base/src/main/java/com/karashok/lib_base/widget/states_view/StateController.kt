package com.karashok.lib_base.widget.states_view

import android.os.Bundle

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name StateController
 * @date 2019/07/31 15:22
 **/
interface StateController {

    /**
     * 加载-加载中View
     * @param bundle
     * @param registerEvent 是否注册事件
     */
    fun addLoading(bundle: Bundle?, registerEvent: Boolean)

    /**
     * 加载-完成View
     * @param bundle
     * @param registerEvent 是否注册事件
     */
    fun addFinish(bundle: Bundle?, registerEvent: Boolean)

    /**
     * 加载-失败View
     * @param bundle
     * @param registerEvent 是否注册事件
     */
    fun addError(bundle: Bundle?, registerEvent: Boolean)

    /**
     * 加载完成-空数据View
     * @param bundle
     * @param registerEvent 是否注册事件
     */
    fun addNullData(bundle: Bundle?, registerEvent: Boolean)

    /**
     * 绑定状态View创建者
     * @param changeView
     */
    fun bindStatesChangeView(changeView: LayoutStatesChangeViewCreater)

    /**
     * 绑定事件回调监听
     * @param eventListener
     */
    fun bindLayoutClickEventListener(eventListener: OnLayoutClickEventListener?)

    fun success()

    /**
     * 获取状态View创建者
     * @return
     */
    fun getStatesChangeViewCreater(): LayoutStatesChangeViewCreater
}
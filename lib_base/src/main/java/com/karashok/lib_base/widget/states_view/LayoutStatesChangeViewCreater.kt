package com.karashok.lib_base.widget.states_view

import android.view.View

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION 状态View创建者
 * @name LayoutStatesChangeViewCreater
 * @date 2019/07/31 15:19
 **/
interface LayoutStatesChangeViewCreater {

    /**
     * 创建-加载中View
     * @return
     */
    fun createLoadingView(): View?

    /**
     * 创建-结束View
     * @return
     */
    fun createFinishView(): View?

    /**
     * 创建-失败View
     * @return
     */
    fun createErrorView(): View?

    /**
     * 创建-空数据View
     * @return
     */
    fun createNullDataView(): View?

    /**
     * 创建-页面View
     * @return
     */
    fun createContentView(): View?

    /**
     * 创建-标题View
     * @return
     */
    fun createTitleView(): View?

    /**
     * 设置状态View的点击事件回调
     * @return
     */
    fun setStateChangeViewClickCallBack(): OnLayoutClickEventListener?
}
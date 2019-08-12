package com.karashok.lib_base.widget.states_view

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.karashok.lib_base.widget.states_view.OnLayoutClickEventListener.Companion.LAYOUT_EVENT_CODE_ERROR
import com.karashok.lib_base.widget.states_view.OnLayoutClickEventListener.Companion.LAYOUT_EVENT_CODE_FINISH
import com.karashok.lib_base.widget.states_view.OnLayoutClickEventListener.Companion.LAYOUT_EVENT_CODE_LOADING
import com.karashok.lib_base.widget.states_view.OnLayoutClickEventListener.Companion.LAYOUT_EVENT_CODE_NULL_DATA

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION 可根据状态更改显示的布局
 * @name StateLayout
 * @date 2019/07/31 15:23
 **/
class StateLayout(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : FrameLayout(context,attrs,defStyleAttr), StateController {

    private lateinit var mStatesChangeViewCreater: LayoutStatesChangeViewCreater
    private var mClickEventListener: OnLayoutClickEventListener? = null

    constructor(context: Context, @Nullable attrs: AttributeSet?): this(context,attrs,0)

    constructor(context: Context): this(context,null)

    override fun addLoading(bundle: Bundle?, registerEvent: Boolean) {
        addStatesView(getStatesChangeViewCreater().createLoadingView(),LAYOUT_EVENT_CODE_LOADING,bundle,registerEvent)
    }

    override fun addFinish(bundle: Bundle?, registerEvent: Boolean) {
        addStatesView(getStatesChangeViewCreater().createFinishView(),LAYOUT_EVENT_CODE_FINISH,bundle,registerEvent)
    }

    override fun addError(bundle: Bundle?, registerEvent: Boolean) {
        addStatesView(getStatesChangeViewCreater().createErrorView(),LAYOUT_EVENT_CODE_ERROR,bundle,registerEvent)
    }

    override fun addNullData(bundle: Bundle?, registerEvent: Boolean) {
        addStatesView(getStatesChangeViewCreater().createNullDataView(),LAYOUT_EVENT_CODE_NULL_DATA,bundle,registerEvent)
    }

    override fun bindStatesChangeView(changeView: LayoutStatesChangeViewCreater) {
        mStatesChangeViewCreater = changeView
    }

    override fun bindLayoutClickEventListener(eventListener: OnLayoutClickEventListener?) {
        mClickEventListener = eventListener
    }

    override fun success() {
        removeAllViews()
    }

    override fun getStatesChangeViewCreater(): LayoutStatesChangeViewCreater {
        return mStatesChangeViewCreater
    }

    /**
     * 根据View的标识添加View
     * @param view
     * @param eventCode 标识
     * @param bundle
     */
    private fun addStatesView(view: View?, eventCode: Int, bundle: Bundle?, registerEvent: Boolean){
        removeAllViews();
        if (registerEvent){
            view?.let {
                if(!it.hasOnClickListeners()){
                    it.setOnClickListener { sendStatesChangeEvent(eventCode,bundle) }
                }
            }
        }else {
            view?.let {
                if(it.hasOnClickListeners()){
                    it.setOnClickListener(null)
                }
            }
        }
        addView(view,createLayoutParams())
    }

    private fun createLayoutParams() : ViewGroup.LayoutParams{
        return LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    /**
     * 发送状态View的点击事件
     * @param eventCode
     * @param bundle
     */
    protected fun sendStatesChangeEvent(eventCode: Int, bundle: Bundle?){
        mClickEventListener?.layoutClickEvent(eventCode,bundle)
    }
}
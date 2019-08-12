package com.karashok.common.ui.aty

import android.app.ActivityManager
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.karashok.common.R
import com.karashok.common.net.State
import com.karashok.common.ui.fgt.CommonBaseLoadingDialogFgt
import com.karashok.common.widget.DefaultTitleView
import com.karashok.lib_base.ui.aty.BaseChangeStatesAty
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_CONTENT
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_ERROR
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_LOADING
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_NULL_DATA
import com.karashok.lib_base.widget.states_view.OnLayoutClickEventListener
import com.karashok.lib_base.widget.states_view.OnLayoutClickEventListener.Companion.LAYOUT_EVENT_CODE_ERROR

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name CommonBaseActivity
 * @date 2019/08/01 20:35
 **/
open abstract class CommonBaseActivity: BaseChangeStatesAty() {

    protected var mTitleView: DefaultTitleView? = null
    private var mIsFullScreen: Boolean = true
    private var mLoadingDialog: CommonBaseLoadingDialogFgt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onCreateInit(savedInstanceState)
        savedInstanceState?.let {
            if (savedInstanceState.containsKey("page_status")) {
                mStatus = savedInstanceState.getInt("page_status")
            }
        }
        if (defaultSuccessState() && mStatus === STATE_TYPE_CONTENT) {
            onContentView()
        } else {
            dispatchStatus()
        }
    }

    private fun dispatchStatus() {
        when (mStatus) {
            STATE_TYPE_ERROR ->{
                onStateError()
            }
            STATE_TYPE_LOADING ->{
                onStateLoading()
            }
            STATE_TYPE_NULL_DATA ->{
                onStateNullData()
            }
        }
    }

    protected fun onCreateInit(savedInstanceState: Bundle?) {

    }

    protected fun defaultSuccessState(): Boolean {
        return true
    }

    override protected fun onViewStateChange(type: Int, bundle: Bundle?) {
        super.onViewStateChange(type, bundle)
        when (type) {
            STATE_TYPE_CONTENT ->{
                assertTitle()
            }
        }
    }

    private fun assertTitle() {
        if (mTitleView != null)
            return
        mTitleView = findViewById(R.id.layout_common_base_default_title_dtv)
        if (mTitleView is DefaultTitleView) {
            (mTitleView as DefaultTitleView).setOnTitleBarListener(object : DefaultTitleView.OnTitleBarListener {
                override fun onLeftClick(view: View) {
                    onBackPressed()
                }

                override fun onTitleClick(view: View) {
                    this@CommonBaseActivity.onTitleClick(view)
                }

                override fun onRightClick(view: View) {
                    onTitleRightClick(view)
                }
            })
            (mTitleView as DefaultTitleView).setTitle(title)
        }
    }

    protected fun onTitleClick(view: View) {

    }

    override fun setTitle(title: CharSequence) {
        super.setTitle(title)
        if (mTitleView != null)
            (mTitleView as DefaultTitleView).setTitle(title)
    }

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)
        if (mTitleView != null)
            (mTitleView as DefaultTitleView).setTitle(titleId)
    }

    protected fun proxyState(state: State) {
        when (state.state) {
            State.STATE_START ->{
                onProxyStateStart(state)
            }
            State.STATE_RESULT ->{
                onProxyStateResult(state)
            }
            State.STATE_RESULT_NULL_DATA ->{
                onProxyStateNullData(state)
            }
            State.STATE_ERROR ->{
                onProxyStateError(state)
            }
        }
    }

    protected fun proxyStateLoadingDialog(state: State) {
        when (state.state) {
            State.STATE_START ->{
                showLoadingDialog()
            }
            State.STATE_RESULT, State.STATE_ERROR ->{
                dismissLoadingDialog()
            }
        }
    }

    protected fun onProxyStateStart(state: State) {
        onStateLoading()
    }

    protected fun onProxyStateResult(state: State) {
        onContentView()
    }

    protected fun onProxyStateNullData(state: State) {
        onStateNullData()
    }

    protected fun onProxyStateError(state: State) {
        onStateError()
    }

    protected fun onTitleRightClick(view: View) {

    }

    protected fun onStateLoading() {
        onStatesLoading(null, true)
    }

    protected fun onStateError() {
        onStatesError(null, true)
    }

    protected fun onStateNullData() {
        onStatesNullData(null, true)
    }

    override fun onCreateLoadingView(): Int {
        return R.layout.layout_common_base_default_loading
    }

    override fun onCreateFinishView(): Int {
        return 0
    }

    override fun onCreateErrorView(): Int {
        return R.layout.layout_common_base_default_error
    }

    override fun onCreateNullDataView(): Int {
        return R.layout.layout_common_base_default_null_data
    }

    override fun onCreateTitleView(): Int {
        return R.layout.layout_common_base_default_title
    }

    override fun setStateChangeViewClickCallBack(): OnLayoutClickEventListener {
        return object : OnLayoutClickEventListener {
            override fun layoutClickEvent(eventCode: Int, bundle: Bundle?) {
                when (eventCode) {
                    LAYOUT_EVENT_CODE_ERROR -> onErrorClick()
                }
            }
        }
    }

    protected abstract fun onErrorClick()

    protected fun fullScreen() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mIsFullScreen = true
    }

    protected fun quitFullScreen() {
        val attrs = window.attributes
        attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
        window.attributes = attrs
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        mIsFullScreen = false
    }

    protected fun isFullScreen(): Boolean {
        return mIsFullScreen
    }

    protected fun keepScreenOn() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    protected fun releaseScreenOn() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    protected fun showLoadingDialog(cancelOnTouchOutside: Boolean) {
        if (mLoadingDialog != null)
            mLoadingDialog?.dismissAllowingStateLoss()
        mLoadingDialog = CommonBaseLoadingDialogFgt()
        mLoadingDialog?.setCancelOnTouchOutside(cancelOnTouchOutside)
        mLoadingDialog?.showAllowingStateLoss(supportFragmentManager)
    }

    protected fun showLoadingDialog() {
        showLoadingDialog(false)
    }

    protected fun dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog?.dismissAllowingStateLoss()
            mLoadingDialog = null
        }
    }

    override protected fun onDestroy() {
        super.onDestroy()

    }

    override protected fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("page_status", mStatus)
    }
}
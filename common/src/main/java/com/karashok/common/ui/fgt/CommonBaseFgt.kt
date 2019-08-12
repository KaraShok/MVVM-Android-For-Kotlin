package com.karashok.common.ui.fgt

import android.os.Bundle
import android.view.View
import com.karashok.common.R
import com.karashok.common.net.State
import com.karashok.common.widget.DefaultTitleView
import com.karashok.lib_base.ui.fgt.BaseChangeStatesFgt
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_CONTENT
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_ERROR
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_LOADING
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_NULL_DATA
import com.karashok.lib_base.widget.states_view.OnLayoutClickEventListener
import com.karashok.lib_base.widget.states_view.OnLayoutClickEventListener.Companion.LAYOUT_EVENT_CODE_ERROR

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name CommonBaseFgt
 * @date 2019/08/02 17:04
 **/
open abstract class CommonBaseFgt: BaseChangeStatesFgt() {

    private var mLoadingDialog: CommonBaseLoadingDialogFgt? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateInit(savedInstanceState)

        val key = "common_base_fgt" + getFragmentTag()
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(key)) {
                mStatus = savedInstanceState.getInt(key)
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

    protected fun getTitleView(): View {
        return findViewById(R.id.layout_common_base_default_title_dtv)
    }

    protected fun defaultSuccessState(): Boolean {
        return true
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

    protected fun onProxyStateError(state: State) {
        onStateError()
    }

    protected fun onProxyStateNullData(state: State) {
        onStateNullData()
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

    override protected fun onCreateLoadingView(): Int {
        return R.layout.layout_common_base_default_loading
    }

    override protected fun onCreateFinishView(): Int {
        return 0
    }

    override protected fun onCreateErrorView(): Int {
        return R.layout.layout_common_base_default_error
    }

    override protected fun onCreateNullDataView(): Int {
        return R.layout.layout_common_base_default_null_data
    }

    override protected fun onCreateTitleView(): Int {
        return 0
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

    protected fun showLoadingDialog(cancelOnTouchOutside: Boolean) {
        if (mLoadingDialog != null)
            mLoadingDialog?.dismissAllowingStateLoss()
        mLoadingDialog = CommonBaseLoadingDialogFgt()
        mLoadingDialog?.setCancelOnTouchOutside(cancelOnTouchOutside)
        mLoadingDialog?.showAllowingStateLoss(childFragmentManager)
    }

    protected fun showLoadingDialog() {
        showLoadingDialog(true)
    }

    protected fun dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog?.dismissAllowingStateLoss()
            mLoadingDialog = null
        }
    }

    protected fun getFragmentTag(): String {
        return this::class.java.simpleName
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val key = "page_status_" + getFragmentTag()
        outState.putInt(key, mStatus)
    }
}
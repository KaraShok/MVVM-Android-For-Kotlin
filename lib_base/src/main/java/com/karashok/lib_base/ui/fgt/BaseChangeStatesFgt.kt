package com.karashok.lib_base.ui.fgt

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleObserver
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import com.karashok.lib_base.R
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_CONTENT
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_ERROR
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_FINISH
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_LOADING
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeCallBack.Companion.STATE_TYPE_NULL_DATA
import com.karashok.lib_base.widget.states_view.LayoutStatesChangeViewCreater
import com.karashok.lib_base.widget.states_view.StateController
import com.karashok.lib_base.widget.states_view.StateLayout
import kotlinx.android.synthetic.main.layout_lib_base_base.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION 基础Fragment
 * @name BaseChangeStatesFgt
 * @date 2019/07/31 15:14
 **/
open abstract class BaseChangeStatesFgt : Fragment(), LayoutStatesChangeViewCreater, LayoutStatesChangeCallBack {

    protected var mFragmentIsLazy: Boolean = false
    private val mViewArrays: SparseArray<View> = SparseArray()
    private var mContentViewInit: Boolean = false
    private val mObserverList: MutableList<LifecycleObserver> = mutableListOf()
    private var mStateController: StateController? = null
    protected var mStatus: Int = STATE_TYPE_CONTENT
    private var mRootView: View? = null
    private var mInflater: LayoutInflater? = null

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null){
            mRootView = inflater.inflate(R.layout.layout_lib_base_base,container,false);
            mInflater = inflater
            mStateController = findViewById(R.id.layout_lib_base_base_state_sl) as StateLayout
            mStateController?.bindStatesChangeView(this)
            mStateController?.bindLayoutClickEventListener(setStateChangeViewClickCallBack())
            if (onCreateTitleView() > 0){
                mInflater?.inflate(onCreateTitleView(), findViewById(R.id.layout_lib_base_base_title_fl), true)
            }
            if (onCreateContentView() > 0){
                mInflater?.inflate(onCreateContentView(), findViewById(R.id.layout_lib_base_base_content_fl), true)
            }
        }
        return mRootView
    }

    /**
     * 获取布局中的控件
     * @param viewId
     * @return
     */
    protected fun <T: View> findViewById(@IdRes viewId: Int): T {
        mRootView?.let {
            return it.findViewById(viewId)
        }
        throw NullPointerException("ContentView is null. ")
    }

    override fun createLoadingView(): View? {
        return initStatesView(onCreateLoadingView())
    }

    override fun createFinishView(): View? {
        return initStatesView(onCreateFinishView())
    }

    override fun createErrorView(): View? {
        return initStatesView(onCreateErrorView())
    }

    override fun createNullDataView(): View? {
        return initStatesView(onCreateNullDataView())
    }

    override fun createContentView(): View? {
        return initStatesView(onCreateContentView())
    }

    override fun createTitleView(): View? {
        return initStatesView(onCreateTitleView())
    }

    /**
     * 初始化布局
     * @param layoutId
     * @return
     */
    private fun initStatesView(layoutId: Int): View?{
        var view: View? = mViewArrays.get(layoutId)
        if (view  == null && layoutId > 0){
            view = layoutInflater.inflate(layoutId,null)
            mViewArrays.put(layoutId,view)
        }
        return view
    }

    /**
     * 设置加载中布局
     * @return
     */
    @LayoutRes
    protected abstract fun onCreateLoadingView(): Int

    /**
     * 设置结束布局
     * @return
     */
    @LayoutRes
    protected abstract fun onCreateFinishView(): Int

    /**
     * 设置失败布局
     * @return
     */
    @LayoutRes
    protected abstract fun onCreateErrorView(): Int

    /**
     * 设置空数据布局
     * @return
     */
    @LayoutRes
    protected abstract fun onCreateNullDataView(): Int

    /**
     * 设置内容布局
     * @return
     */
    @LayoutRes
    protected abstract fun onCreateContentView(): Int

    /**
     * 设置标题布局
     * @return
     */
    @LayoutRes
    protected abstract fun onCreateTitleView(): Int

    override fun onStatesLoading(bundle: Bundle?, registerEvent: Boolean) {
        setStatesView(STATE_TYPE_LOADING,bundle,registerEvent);
    }

    override fun onStatesFinish(bundle: Bundle?, registerEvent: Boolean) {
        setStatesView(STATE_TYPE_FINISH,bundle,registerEvent);
    }

    override fun onStatesError(bundle: Bundle?, registerEvent: Boolean) {
        setStatesView(STATE_TYPE_ERROR,bundle,registerEvent);
    }

    override fun onStatesNullData(bundle: Bundle?, registerEvent: Boolean) {
        setStatesView(STATE_TYPE_NULL_DATA,bundle,registerEvent);
    }

    override fun onContentView() {
        val stateSuccess = setStatesView(STATE_TYPE_CONTENT, null, true)
        if (!mFragmentIsLazy && stateSuccess) {
            if (!mContentViewInit) {
                onContentViewInit()
                onInitData()
                mContentViewInit = true
            }
        }
    }

    protected abstract fun onContentViewInit()

    protected abstract fun onInitData()

    /**
     * 加载状态布局或内容布局
     * @param type 状态标识
     * @param bundle
     * @param registerEvent 是否注册事件
     */
    private fun setStatesView(type: Int, bundle: Bundle?, registerEvent: Boolean): Boolean{
        mStateController?.let {
            mStatus = type
            when(type){
                STATE_TYPE_LOADING->{
                    it.addLoading(bundle, registerEvent)
                }
                STATE_TYPE_FINISH->{
                    it.addFinish(bundle, registerEvent)
                }
                STATE_TYPE_ERROR->{
                    it.addError(bundle, registerEvent)
                }
                STATE_TYPE_NULL_DATA->{
                    it.addNullData(bundle, registerEvent)
                }
                STATE_TYPE_CONTENT->{
                    it.success()
                }
            }
            onViewStateChange(type, bundle)
            return true
        }
        return false
    }

    protected fun onViewStateChange(type: Int, bundle: Bundle?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mViewArrays.clear()
        unbindLifeObserver()
        mObserverList.clear()
    }

    /**
     * 关闭当前宿主Activity
     */
    fun finishActivity() {
        activity?.finish()
    }

    /**
     * 绑定生命周期监听
     * @param observer
     */
    protected fun bindLifeObserver(observer: LifecycleObserver){
        mObserverList.add(observer)
        lifecycle.addObserver(observer)
    }

    /**
     * 解绑生命周期监听
     */
    protected fun unbindLifeObserver(){
        mObserverList.forEach { lifecycle.removeObserver(it) }
        mObserverList.clear()
    }
}
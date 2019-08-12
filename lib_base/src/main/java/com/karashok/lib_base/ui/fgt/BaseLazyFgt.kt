package com.karashok.lib_base.ui.fgt

import android.content.Context
import android.os.Bundle
import androidx.annotation.NonNull

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION 基础懒加载Fragment
 * @name BaseLazyFgt
 * @date 2019/08/01 17:55
 **/
open abstract class BaseLazyFgt : BaseChangeStatesFgt() {

    /**
     * 当前Fragment是否可见
     */
    private var mIsVisible: Boolean = false

    /**
     * 是否与View建立起映射关系
     */
    private var mIsInitView: Boolean = false

    /**
     * 是否是第一次加载
     */
    private var mIsFirstLoad: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentIsLazy = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mIsInitView = true
        if (mIsFirstLoad) {
            lazyLoadData()
        } else if (mIsVisible && mIsInitView) {
            lazyLoad()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            mIsVisible = true
            lazyLoadData()
        } else {
            mIsVisible = false
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

    private fun lazyLoadData() {
        if (mIsFirstLoad) {
            //            Log.d(mStr,"第一次加载 " + " isInitView = " + isInitView + "  isVisible = " + isVisible + "   " + "BaseLazyFgt");
        } else {
            //            Log.d(mStr,"不是第一次加载" + " isInitView = " + isInitView + "  isVisible = " + isVisible + "   " + "BaseLazyFgt");
        }
        if (!mIsFirstLoad || !isVisible || !mIsInitView) {
            //            Log.d(mStr,"不加载" + "   " + "BaseLazyFgt");
            //            Log.d(mStr,"不加载" + "   " + "isFirstLoad：" + isFirstLoad);
            //            Log.d(mStr,"不加载" + "   " + "isVisible：" + isVisible);
            //            Log.d(mStr,"不加载" + "   " + "isInitView：" + isInitView);
            return
        }
        //        Log.d(mStr,"完成数据第一次加载");
        onContentViewInit()
        onInitData()
        mIsFirstLoad = false
    }

    protected open fun lazyLoad() {
        //        Log.d(mStr,"重新加载");
    }

    override fun onAttach(@NonNull context: Context) {
        super.onAttach(context)
        mIsVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mIsInitView = false
        mIsVisible = false
    }
}
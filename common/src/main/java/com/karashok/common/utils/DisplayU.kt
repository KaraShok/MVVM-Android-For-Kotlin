package com.karashok.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration
import android.view.WindowManager

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name DisplayU
 * @date 2019/08/03 17:04
 **/
object DisplayU {

    @SuppressLint("StaticFieldLeak")
    private var sContext: Context? = null

    fun init(context: Context?) {
        if (null != context) {
            sContext = context.applicationContext
        }
    }

    private fun checkNullContext() {
        if (null == sContext) {
            throw RuntimeException("DisplayU...context is null, 请在init方法中传入context")
        }
    }

    fun getScreenHeight(): Int {
        checkNullContext()
        var dm = sContext!!.resources.displayMetrics
        if (Build.VERSION.SDK_INT > 16) {
            //适配全面屏幕（包括虚拟功能高度）
            val wm = sContext!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            if (null != wm) {
                val display = wm.defaultDisplay
                dm = DisplayMetrics()
                display.getRealMetrics(dm)
            }
        }
        return dm.heightPixels
    }

    fun getScreenWidth(): Int {
        checkNullContext()
        return sContext!!.resources.displayMetrics.widthPixels
    }

    fun getScreenWidthDp(): Int {
        checkNullContext()
        return px2dp(getScreenWidth().toFloat())
    }

    fun getScreenHeightDp(): Int {
        checkNullContext()
        return px2dp(getScreenHeight().toFloat())
    }

    fun dp2px(dpValue: Float): Int {
        val scale = getDensity()
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dp(pxValue: Float): Int {
        val scale = getDensity()
        return (pxValue / scale + 0.5f).toInt()
    }

    fun px2sp(pxValue: Float): Int {
        val scale = getDensity()
        return (pxValue / scale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Int {
        val scale = getDensity()
        return (spValue * scale + 0.5f).toInt()
    }

    fun getDpi(): Int {
        checkNullContext()
        return sContext!!.resources.displayMetrics.densityDpi
    }

    fun getDensity(): Float {
        checkNullContext()
        return sContext!!.resources.displayMetrics.density
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(): Int {
        checkNullContext()
        // 默认为38
        var height = 38
        //获取status_bar_height资源的ID
        val resourceId = sContext!!.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0)
            height = sContext!!.resources.getDimensionPixelSize(resourceId)
        Log.e("Hubert", "状态栏的高度:$height")
        return height
    }

    /**
     * 虚拟操作拦（home等）是否显示
     */
    fun isNavigationBarShow(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            val realSize = Point()
            display.getSize(size)
            display.getRealSize(realSize)
            return realSize.y != size.y
        } else {
            val menu = ViewConfiguration.get(activity).hasPermanentMenuKey()
            val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            return if (menu || back) {
                false
            } else {
                true
            }
        }
    }

    /**
     * 获取虚拟操作拦（home等）高度
     */
    fun getNavigationBarHeight(activity: Activity): Int {
        if (!isNavigationBarShow(activity))
            return 0
        var height = 0
        val resources = activity.resources
        //获取NavigationBar的高度
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0)
            height = resources.getDimensionPixelSize(resourceId)
        Log.e("Hubert", "NavigationBar的高度:$height")
        return height
    }
}
package com.karashok.common.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.Toast

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ToastU
 * @date 2019/08/03 15:30
 **/
object ToastU {

    private val LENGTH_SHORT_TIME: Long = 2000

    private var toast: Toast? = null

    private var mHandler: Handler? = null

    var mDuration = Toast.LENGTH_LONG

    private var mCustomDuration: Long = -1

    var mMsg: String? = null

    var mContext: Context? = null

    fun initToast(context: Context?) {
        if (toast == null) {
            mContext = context?.applicationContext
            mHandler = Handler(Looper.getMainLooper())
            mHandler!!.post {
                if (mContext != null) {
                    toast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT)
                    toast!!.setGravity(Gravity.CENTER, 0, 0)
                }
            }
        }
    }

    private fun show(duration: Int, msg: String) {
        if (TextUtils.isEmpty(msg)) {
            return
        }
        if (null == mContext) {
            return
        }
        mDuration = duration
        mMsg = msg
        mHandler!!.removeCallbacks(showRunnable)
        mHandler!!.postDelayed(showRunnable, 100)
    }

    private fun showWithView(duration: Int, view: View?, gravity: Int, yOffset: Int) {
        if (view == null) {
            return
        }
        if (null == mContext) {
            return
        }
        mDuration = duration
        toast!!.view = view
        toast!!.setGravity(gravity, 0, yOffset)
        mHandler!!.removeCallbacks(showRunnable)
        mHandler!!.postDelayed(showRunnable, 100)
    }

    private val showRunnable = object : Runnable {

        override fun run() {
            if (toast != null && mContext != null) {
                val view = toast!!.view
                toast!!.cancel()
                toast = Toast(mContext)
                toast!!.setGravity(Gravity.CENTER, 0, 0)
                toast!!.view = view
                toast!!.duration = mDuration
                if (!TextUtils.isEmpty(mMsg)) {
                    toast!!.setText(mMsg)
                }
                toast!!.show()

                if (mCustomDuration <= 0) {
                    if (mCustomDuration == 0L) {
                        hide()
                    }
                    return
                }

                if (mCustomDuration > LENGTH_SHORT_TIME) {
                    mHandler!!.postDelayed(this, LENGTH_SHORT_TIME)
                    mCustomDuration -= LENGTH_SHORT_TIME
                } else {
                    mHandler!!.postDelayed(this, mCustomDuration)
                    mCustomDuration = 0
                }

            }
        }
    }

    fun hide() {
        if (null != toast) {
            toast!!.cancel()
            mDuration = Toast.LENGTH_LONG
            mCustomDuration = -1
        }
    }

    fun showShort(resID: Int) {
        show(Toast.LENGTH_SHORT, mContext!!.getString(resID))
    }

    fun showShort(msg: String) {
        show(Toast.LENGTH_SHORT, msg)
    }

    fun showLong(resID: Int) {
        show(Toast.LENGTH_LONG, mContext!!.getString(resID))
    }

    fun showLong(msg: String) {
        show(Toast.LENGTH_LONG, msg)
    }

    fun show(resID: Int, customDuration: Long) {
        mCustomDuration = customDuration
        show(Toast.LENGTH_LONG, mContext!!.getString(resID))
    }

    fun show(msg: String, customDuration: Long) {
        mCustomDuration = customDuration
        show(Toast.LENGTH_LONG, msg)
    }

    fun showShortWithView(view: View, gravity: Int, yOffset: Int) {
        showWithView(Toast.LENGTH_SHORT, view, gravity, yOffset)
    }

    fun showLongWithView(view: View, gravity: Int, yOffset: Int) {
        showWithView(Toast.LENGTH_LONG, view, gravity, yOffset)
    }

    fun showWithView(view: View, customDuration: Int, gravity: Int, yOffset: Int) {
        showWithView(customDuration, view, gravity, yOffset)
    }
}
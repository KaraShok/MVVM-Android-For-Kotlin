package com.karashok.lib_base.widget.cancelable_dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewConfiguration

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name CancelableDialog
 * @date 2019/08/01 20:46
 **/
class CancelableDialog(context: Context,val themeResId: Int): Dialog(context, themeResId) {

    private val GRAVITY_ATTR: IntArray = IntArray(android.R.attr.gravity)

    var mShouldCancelCallback: ShouldCancelCallback? = null
    var mPreCancelCallback: PreCancelCallback? = null

    constructor(context: Context): this(context, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val a = context.obtainStyledAttributes(themeResId, GRAVITY_ATTR)
        val gravity = a.getInt(0, Gravity.CENTER)
        val window = window
        window!!.setGravity(gravity)
        a.recycle()
    }

    override fun onTouchEvent(@NonNull event: MotionEvent): Boolean {
        if (isShowing && shouldCloseOnTouch(context, event)) {
            cancel()
            return true
        }
        return false
    }

    /*
     * copy from Window.java
     */
    private fun shouldCloseOnTouch(context: Context, event: MotionEvent): Boolean {
        return (mShouldCancelCallback?.shouldCancelOnTouchOutside() ?: false &&
                event.action == MotionEvent.ACTION_DOWN &&
                isOutOfBounds(context, event) &&
                (window == null || window!!.peekDecorView() != null))
    }

    /*
     * copy from Window.java
     */
    private fun isOutOfBounds(context: Context, event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        val slop = ViewConfiguration.get(context).scaledWindowTouchSlop
        if (window == null) return true
        val decorView = window!!.decorView
        return (x < -slop || y < -slop
                || x > decorView.width + slop
                || y > decorView.height + slop)
    }

    override fun onBackPressed() {
        if (mShouldCancelCallback?.shouldCancelOnBackPressed() ?: false) {
            cancel()
        }
    }

    override fun cancel() {
        mPreCancelCallback?.let {
            it.onPreCancel()
        }
        super.cancel()
    }
}
package com.karashok.lib_base.ui.fgt

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.karashok.lib_base.R
import com.karashok.lib_base.widget.cancelable_dialog.CancelableDialog
import com.karashok.lib_base.widget.cancelable_dialog.DismissListener
import com.karashok.lib_base.widget.cancelable_dialog.PreCancelCallback
import com.karashok.lib_base.widget.cancelable_dialog.ShouldCancelCallback

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name BaseDialogFgt
 * @date 2019/08/01 20:44
 **/
open abstract class BaseDialogFgt: DialogFragment(), PreCancelCallback, ShouldCancelCallback {

    companion object{
        val DEFAULT_DIM: Float = 0.8F
    }

    private var mDismissListener: DismissListener? = null
    private var mCancelOnTouchOutside: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, theme)
    }

    /**
     * 如果需要设置主题，请重写这个函数，并返回一个主题
     */
    override fun getTheme(): Int {
        return R.style.styleLibBaseDialogFgt
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = CancelableDialog(requireActivity(), theme)
        val window = dialog.window
        window.setDimAmount(getDimAmount())
        dialog.mPreCancelCallback = this
        dialog.mShouldCancelCallback = this
        return super.onCreateDialog(savedInstanceState)
    }

    /**
     * dialog 非内容区域的黑色透明度，默认 0.8
     */
    protected open fun getDimAmount(): Float {
        return DEFAULT_DIM
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(getLayoutRes(),container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    override fun show(transaction: FragmentTransaction, tag: String?): Int {
        return super.show(transaction, tag)
    }

    /**
     * 安全的显示 dialog
     */
    fun showAllowingStateLoss(fmgr: FragmentManager) {
        show(fmgr, getFragmentTag())
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun bindView(v: View)

    /**
     * 点击返回按钮， dialog 是否应该消失
     */
    override fun shouldCancelOnBackPressed(): Boolean {
        return true
    }

    /**
     * 点击 dialog 的 外围 是否应该消失
     */
    override fun shouldCancelOnTouchOutside(): Boolean {
        return mCancelOnTouchOutside
    }

    fun setCancelOnTouchOutside(cancelOnTouchOutside: Boolean) {
        mCancelOnTouchOutside = cancelOnTouchOutside
    }

    /**
     * cancel 之前的回调
     */
    override fun onPreCancel() {}

    override fun dismiss() {
        dismissAllowingStateLoss()
    }

    fun setOnDismissListener(listener: DismissListener) {
        mDismissListener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mDismissListener?.let {
            it.onDismiss()
        }
    }

    fun getFragmentTag(): String {
        return "lib_base_dialog_fgt_" + getIdentityString()
    }

    protected fun getIdentityString(): String {
        return Integer.toHexString(System.identityHashCode(this))
    }
}
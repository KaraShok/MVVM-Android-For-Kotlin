package com.karashok.lib_base.ui.fgt

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.karashok.lib_base.R

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION 底部弹出的fragment
 * @name BaseBottomDialogFgt
 * @date 2019/08/02 10:33
 **/
open abstract class BaseBottomDialogFgt: BaseDialogFgt() {

    fun getCancelOutside(): Boolean {
        return true
    }

    override fun getTheme(): Int {
        return R.style.styleLibBaseBottomDialogFg
    }

    fun show(fragmentManager: FragmentManager) {
        showAllowingStateLoss(fragmentManager)
    }

    fun hide() {
        dismissAllowingStateLoss()
    }

    override fun shouldCancelOnTouchOutside(): Boolean {
        return getCancelOutside()
    }

    override fun onStart() {
        super.onStart()
        // 由于 Dialog 创建在先，所以布局未充满。导致布局始终为 wrap_content，强制其 match_parent
        requireDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
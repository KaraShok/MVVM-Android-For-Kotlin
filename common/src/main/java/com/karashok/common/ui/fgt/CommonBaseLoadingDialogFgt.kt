package com.karashok.common.ui.fgt

import android.view.View
import com.karashok.common.R
import com.karashok.lib_base.ui.fgt.BaseDialogFgt

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name CommonBaseLoadingDialogFgt
 * @date 2019/08/02 10:52
 **/
class CommonBaseLoadingDialogFgt: BaseDialogFgt() {

//    init {
//        setCancelOnTouchOutside(false)
//    }

    override fun getLayoutRes(): Int {
       return R.layout.layout_common_base_loading_dialog_fgt
    }

    override fun bindView(v: View) {

    }

//    override fun shouldCancelOnBackPressed(): Boolean {
//        return false
//    }

    protected override fun getDimAmount(): Float {
        return 0.3f
    }
}
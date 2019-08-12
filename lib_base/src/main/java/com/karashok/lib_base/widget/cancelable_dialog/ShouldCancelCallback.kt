package com.karashok.lib_base.widget.cancelable_dialog

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ShouldCancelCallback
 * @date 2019/08/01 21:04
 **/
interface ShouldCancelCallback {

    fun shouldCancelOnBackPressed(): Boolean

    fun shouldCancelOnTouchOutside(): Boolean
}
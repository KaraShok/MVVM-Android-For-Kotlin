package com.karashok.common.utils

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import kotlin.properties.Delegates

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name CommonU
 * @date 2019/08/03 17:11
 **/
object CommonU {

    var mAppInstance: Application by Delegates.notNull()

    fun init(instance: Application){
        mAppInstance = instance
        UserInfoHelper.init(instance)
        ToastU.initToast(instance)
        DisplayU.init(instance)
        Fresco.initialize(instance)
    }
}
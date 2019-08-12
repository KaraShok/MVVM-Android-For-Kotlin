package com.karashok.mvvm

import android.app.Application
import android.content.Context
import com.karashok.common.utils.CommonU
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.*
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.scwang.smartrefresh.layout.header.BezierRadarHeader

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name App
 * @date 2019/08/03 11:46
 **/
class App : Application() {

    // static 代码段可以防止内存泄露
    companion object{
        init {
            // 设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator{
                override fun createRefreshHeader(context: Context, layout: RefreshLayout): RefreshHeader {
                    val bezierRadarHeader = BezierRadarHeader(context)
                    bezierRadarHeader.setPrimaryColorId(R.color.color_33AAFF)
                    return bezierRadarHeader
                }
            })
            // 设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator(object : DefaultRefreshFooterCreator{
                override fun createRefreshFooter(context: Context, layout: RefreshLayout): RefreshFooter {
                    val ballPulseFooter = BallPulseFooter(context)
                    ballPulseFooter.setAnimatingColor(0xFF33AAFF.toInt())
                    return ballPulseFooter
                }

            })
        }
    }

    override fun onCreate() {
        super.onCreate()
        CommonU.init(this)
    }
}
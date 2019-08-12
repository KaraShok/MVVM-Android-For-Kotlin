package com.karashok.mvvm.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.TextView
import com.karashok.mvvm.R
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import org.jetbrains.anko.padding
import org.jetbrains.anko.textColorResource

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name HotSearchFgtTagFlowLayoutContentAdapter
 * @date 2019/08/08 20:31
 **/
class HotSearchFgtTagFlowLayoutContentAdapter(val context: Context,tags: MutableList<String>) : TagAdapter<String>(tags)  {

    override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
        val textView: TextView = TextView(context)
        val layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(5,5,5,5)
        textView.layoutParams = layoutParams
        textView.setText(t)
        textView.textSize = 18F
        textView.textColorResource = R.color.colorPrimary
        textView.padding = 10
        return textView
    }
}
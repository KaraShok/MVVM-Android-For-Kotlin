package com.karashok.common.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.karashok.common.R
import kotlinx.android.synthetic.main.layout_common_base_default_title_view.view.*

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name DefaultTitleView
 * @date 2019/08/02 16:14
 **/
class DefaultTitleView(@NonNull context: Context, @Nullable attrs: AttributeSet?): RelativeLayout(context,attrs) {

    companion object{
        val MODE_NONE = 0
        val MODE_TEXT = 1
        val MODE_ICON = 2

        //right mode private
        val MODE_CUST = 3
    }

    constructor(@NonNull context: Context): this(context,null)

    private var leftMode = MODE_ICON
    private var rightMode = MODE_TEXT

    private var onTitleBarListener: OnTitleBarListener? = null

    init {
        val titleView = LayoutInflater.from(context).inflate(R.layout.layout_common_base_default_title_view, this, true)
        layout_common_base_default_title_view_left_text_tv.setOnClickListener { v ->
            if (leftMode == MODE_TEXT && onTitleBarListener != null) {
                onTitleBarListener?.onLeftClick(v)
            }
        }
        layout_common_base_default_title_view_left_icon_iv.setOnClickListener { v ->
            if (leftMode == MODE_ICON && onTitleBarListener != null) {
                onTitleBarListener?.onLeftClick(v)
            }
        }
        layout_common_base_default_title_view_title_iv.setOnClickListener { v ->
            if (onTitleBarListener != null) {
                onTitleBarListener?.onTitleClick(v)
            }
        }
        layout_common_base_default_title_view_right_text_tv.setOnClickListener { v ->
            if (rightMode == MODE_TEXT && onTitleBarListener != null) {
                onTitleBarListener?.onRightClick(v)
            }
        }
        layout_common_base_default_title_view_right_icon_iv.setOnClickListener { v ->
            if (rightMode == MODE_ICON && onTitleBarListener != null) {
                onTitleBarListener?.onRightClick(v)
            }
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DefaultTitleView)

        parseLeftAttrs(typedArray)
        parseTitleAttrs(typedArray)
        parseRightAttrs(typedArray)

        typedArray.recycle()
    }

    private fun parseLeftAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.DefaultTitleView_leftMode)) {
            leftMode = typedArray.getInt(R.styleable.DefaultTitleView_leftMode, MODE_ICON)
            setLeftMode(leftMode)
        }
        if (typedArray.hasValue(R.styleable.DefaultTitleView_leftViewPadding)) {
            val paddingString = typedArray.getString(R.styleable.DefaultTitleView_leftViewPadding)
            val paddingArray = parPaddingString(paddingString)
            var leftView: View = layout_common_base_default_title_view_left_icon_iv
            if (leftMode == MODE_TEXT) {
                leftView = layout_common_base_default_title_view_left_text_tv
            }
            leftView.setPadding(paddingArray[0], paddingArray[1], paddingArray[2], paddingArray[3])
        }
        if (typedArray.hasValue(R.styleable.DefaultTitleView_leftTextColor)) {
            layout_common_base_default_title_view_left_text_tv.setTextColor(typedArray.getColor(R.styleable.DefaultTitleView_leftTextColor, Color.BLACK))
        }
        if (typedArray.hasValue(R.styleable.DefaultTitleView_leftTextSize)) {
            val defaultLeftTextSize = dp2px(context, 14f)
            val textSize =
                typedArray.getDimensionPixelOffset(R.styleable.DefaultTitleView_leftTextSize, defaultLeftTextSize)
            layout_common_base_default_title_view_left_text_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        }
        if (typedArray.hasValue(R.styleable.DefaultTitleView_leftText)) {
            layout_common_base_default_title_view_left_text_tv.text = typedArray.getText(R.styleable.DefaultTitleView_leftText)
        }

        if (typedArray.hasValue(R.styleable.DefaultTitleView_leftIcon)) {
            layout_common_base_default_title_view_left_icon_iv.setImageResource(typedArray.getResourceId(R.styleable.DefaultTitleView_leftIcon, 0))
        }
    }

    private fun strToInt(s: String): Int {
        var r = 0
        try {
            r = Integer.parseInt(s)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return r
    }

    private fun parPaddingString(paddingString: String?): IntArray {

        val result = IntArray(4)
        if (!TextUtils.isEmpty(paddingString)) {
            val split = paddingString!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var len = split.size
            len = if (len > 4) 4 else len
            for (i in 0 until len) {
                result[i] = dp2px(context, strToInt(split[i]).toFloat())
            }
        }
        return result
    }

    private fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    private fun parseTitleAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.DefaultTitleView_titleViewPadding)) {
            val paddingString = typedArray.getString(R.styleable.DefaultTitleView_titleViewPadding)
            val paddingArray = parPaddingString(paddingString)
            layout_common_base_default_title_view_title_iv.setPadding(paddingArray[0], paddingArray[1], paddingArray[2], paddingArray[3])
        }
        if (typedArray.hasValue(R.styleable.DefaultTitleView_title_TextColor)) {
            layout_common_base_default_title_view_title_iv.setTextColor(typedArray.getColor(R.styleable.DefaultTitleView_title_TextColor, Color.BLACK))
        }
        if (typedArray.hasValue(R.styleable.DefaultTitleView_titleTextSize)) {
            val defaultLeftTextSize = dp2px(context, 18f)
            val textSize =
                typedArray.getDimensionPixelOffset(R.styleable.DefaultTitleView_titleTextSize, defaultLeftTextSize)
            layout_common_base_default_title_view_title_iv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        }
        if (typedArray.hasValue(R.styleable.DefaultTitleView_titleText)) {
            layout_common_base_default_title_view_title_iv.text = typedArray.getText(R.styleable.DefaultTitleView_titleText)
        }
    }

    private fun parseRightAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.DefaultTitleView_rightMode)) {
            rightMode = typedArray.getInt(R.styleable.DefaultTitleView_rightMode, MODE_TEXT)
            setRightMode(rightMode)
        }
        if (typedArray.hasValue(R.styleable.DefaultTitleView_rightViewPadding)) {
            val paddingString = typedArray.getString(R.styleable.DefaultTitleView_rightViewPadding)
            val paddingArray = parPaddingString(paddingString)
            var rightView: View = layout_common_base_default_title_view_right_icon_iv
            if (rightMode == MODE_TEXT) {
                rightView = layout_common_base_default_title_view_right_text_tv
            }
            rightView.setPadding(paddingArray[0], paddingArray[1], paddingArray[2], paddingArray[3])
        }
        if (typedArray.hasValue(R.styleable.DefaultTitleView_rightTextColor)) {
            layout_common_base_default_title_view_right_text_tv.setTextColor(typedArray.getColor(R.styleable.DefaultTitleView_rightTextColor, Color.BLACK))
        }
        if (typedArray.hasValue(R.styleable.DefaultTitleView_rightTextSize)) {
            val defaultLeftTextSize = dp2px(context, 14f)
            val textSize =
                typedArray.getDimensionPixelOffset(R.styleable.DefaultTitleView_rightTextSize, defaultLeftTextSize)
            layout_common_base_default_title_view_right_text_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        }
        if (typedArray.hasValue(R.styleable.DefaultTitleView_rightText)) {
            layout_common_base_default_title_view_right_text_tv.text = typedArray.getText(R.styleable.DefaultTitleView_rightText)
        }

        if (typedArray.hasValue(R.styleable.DefaultTitleView_rightIcon)) {
            layout_common_base_default_title_view_right_icon_iv.setImageResource(typedArray.getResourceId(R.styleable.DefaultTitleView_rightIcon, 0))
        }
    }

    fun setLeftMode(mode: Int) {
        when (mode) {
            MODE_TEXT -> {
                layout_common_base_default_title_view_left_text_tv.visibility = View.VISIBLE
                layout_common_base_default_title_view_left_icon_iv.visibility = View.GONE
            }
            MODE_ICON -> {
                layout_common_base_default_title_view_left_text_tv.visibility = View.GONE
                layout_common_base_default_title_view_left_icon_iv.visibility = View.VISIBLE
            }
        }
        leftMode = mode
    }

    fun setLeftRes(resId: Int) {
        when (leftMode) {
            MODE_TEXT ->{
                layout_common_base_default_title_view_left_text_tv.setText(resId)
            }
            MODE_ICON ->{
                layout_common_base_default_title_view_left_icon_iv.setImageResource(resId)
            }
        }
    }

    fun setLeftViewStyle(style: ViewStyle) {
        when (leftMode) {
            MODE_TEXT ->{
                viewToStyle(layout_common_base_default_title_view_left_text_tv, style)
            }
            MODE_ICON ->{
                viewToStyle(layout_common_base_default_title_view_left_icon_iv, style)
            }
        }
    }

    private fun viewToStyle(view: View?, style: ViewStyle?) {
        if (view == null || style == null)
            return
        if (view is TextView) {
            if (style!!.getTextColor() > 0)
                view.setTextColor(style!!.getTextColor())
            if (style!!.getTextSize() > 0)
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, style!!.getTextSize())
        }
        var paddingLeft = view.paddingLeft
        var paddingTop = view.paddingTop
        var paddingRight = view.paddingRight
        var paddingBottom = view.paddingBottom
        if (style!!.getPaddingLeft() > 0) {
            paddingLeft = style!!.getPaddingLeft()
        }
        if (style!!.getPaddingTop() > 0) {
            paddingTop = style!!.getPaddingTop()
        }
        if (style!!.getPaddingRight() > 0) {
            paddingRight = style!!.getPaddingRight()
        }
        if (style!!.getPaddingBottom() > 0) {
            paddingBottom = style!!.getPaddingBottom()
        }
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    fun setTitle(title: CharSequence) {
        layout_common_base_default_title_view_title_iv.text = title
    }

    fun setTitleTextColor(@ColorRes colorId: Int) {
        layout_common_base_default_title_view_title_iv.setTextColor(context.resources.getColor(colorId))
    }

    fun setTitle(resId: Int) {
        setTitle(context.getString(resId))
    }

    fun setTitleViewStyle(style: ViewStyle) {
        viewToStyle(layout_common_base_default_title_view_title_iv, style)
    }

    fun setRightMode(mode: Int) {
        when (mode) {
            MODE_TEXT -> {
                layout_common_base_default_title_view_right_text_tv.visibility = View.VISIBLE
                layout_common_base_default_title_view_right_icon_iv.visibility = View.GONE
            }
            MODE_ICON -> {
                layout_common_base_default_title_view_right_text_tv.visibility = View.GONE
                layout_common_base_default_title_view_right_icon_iv.visibility = View.VISIBLE
            }
            else -> {
                layout_common_base_default_title_view_right_text_tv.visibility = View.GONE
                layout_common_base_default_title_view_right_icon_iv.visibility = View.GONE
            }
        }
        rightMode = mode
    }

    fun setRightRes(resId: Int) {
        when (rightMode) {
            MODE_TEXT ->{
                layout_common_base_default_title_view_right_text_tv.setText(resId)
            }
            MODE_ICON ->{
                layout_common_base_default_title_view_right_icon_iv.setImageResource(resId)
            }
        }
    }

    fun setRightViewStyle(style: ViewStyle) {
        when (rightMode) {
            MODE_TEXT ->{
                viewToStyle(layout_common_base_default_title_view_right_text_tv, style)
            }
            MODE_ICON ->{
                viewToStyle(layout_common_base_default_title_view_right_icon_iv, style)
            }
        }
    }

    fun setRightCustomLayoutRes(layoutId: Int, addClickIds: IntArray, onClickListener: View.OnClickListener) {
        setRightMode(MODE_CUST)
        val view = LayoutInflater.from(context).inflate(layoutId, layout_common_base_default_title_view_right__custom_container_fl, true)
        if (addClickIds.size > 0) {
            for (id in addClickIds) {
                view.findViewById<View>(id).isClickable = true
                view.findViewById<View>(id).setOnClickListener(onClickListener)
            }
        }
    }

    fun <T : View> getRightView(id: Int): T {
        return layout_common_base_default_title_view_right__custom_container_fl.findViewById(id)
    }

    fun setLeftModeState(show: Boolean) {
        layout_common_base_default_title_view_left_container_fl.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setOnTitleBarListener(listener: OnTitleBarListener) {
        this.onTitleBarListener = listener
    }

    interface OnTitleBarListener {
        fun onLeftClick(view: View)

        fun onTitleClick(view: View)

        fun onRightClick(view: View)
    }
}
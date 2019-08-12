package com.karashok.common.widget

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ViewStyle
 * @date 2019/08/02 16:31
 **/
class ViewStyle {

    private var textColor = -1
    private var textSize = -1F

    private var paddingLeft = -1
    private var paddingTop = -1
    private var paddingRight = -1
    private var paddingBottom = -1

    fun getTextColor(): Int {
        return textColor
    }

    fun setTextColor(textColor: Int): ViewStyle {
        this.textColor = textColor
        return this
    }

    fun getTextSize(): Float {
        return textSize
    }

    fun setTextSize(textSize: Float): ViewStyle {
        this.textSize = textSize
        return this
    }

    fun getPaddingLeft(): Int {
        return paddingLeft
    }

    fun setPaddingLeft(paddingLeft: Int): ViewStyle {
        this.paddingLeft = paddingLeft
        return this
    }

    fun getPaddingTop(): Int {
        return paddingTop
    }

    fun setPaddingTop(paddingTop: Int): ViewStyle {
        this.paddingTop = paddingTop
        return this
    }

    fun getPaddingRight(): Int {
        return paddingRight
    }

    fun setPaddingRight(paddingRight: Int): ViewStyle {
        this.paddingRight = paddingRight
        return this
    }

    fun getPaddingBottom(): Int {
        return paddingBottom
    }

    fun setPaddingBottom(paddingBottom: Int): ViewStyle {
        this.paddingBottom = paddingBottom
        return this
    }
}
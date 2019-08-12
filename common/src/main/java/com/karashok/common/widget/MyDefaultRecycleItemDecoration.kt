package com.karashok.common.widget

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.karashok.common.utils.CommonU
import com.karashok.common.utils.DisplayU

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name MyDefaultRecycleItemDecoration
 * @date 2019/08/03 16:46
 **/
class MyDefaultRecycleItemDecoration(orientation: Int, decorationColor: Int, decorationHeightDP: Int,
                                     marginColor: Int , marginDP: Int): RecyclerView.ItemDecoration() {

    companion object{
        val ITEM_HORIZONTAL = LinearLayout.HORIZONTAL
        val ITEM_VERTICAL = LinearLayout.VERTICAL
    }

    private var mOrientation: Int = 0

    /**
     * 分割线缩进值
     */
    private var mMarginPX: Int = 0
    /**
     * 分割线高度
     */
    private var mDecorationHeightPX: Int

    /**
     * 缩进画笔
     */
    private var mMarginPaint: Paint? = null
    /**
     * 分割线画笔
     */
    private var mDecorationPaint: Paint

    private var mStartItemPosition = 0

    init {
        if (marginDP > 0) {
            mMarginPX = DisplayU.dp2px(marginDP.toFloat())

            mMarginPaint = Paint()
            mMarginPaint?.setColor(CommonU.mAppInstance.getResources().getColor(marginColor))
            mMarginPaint?.setStyle(Paint.Style.FILL)
            mMarginPaint?.setAntiAlias(true)
        }

        mDecorationHeightPX = DisplayU.dp2px(decorationHeightDP.toFloat())

        mDecorationPaint = Paint()
        mDecorationPaint.color = CommonU.mAppInstance.getResources().getColor(decorationColor)
        mDecorationPaint.style = Paint.Style.FILL
        mDecorationPaint.isAntiAlias = true
        setOrientation(orientation)
    }

    private fun setOrientation(orientation: Int) {
        if (orientation != ITEM_HORIZONTAL && orientation != ITEM_VERTICAL) {
            throw IllegalArgumentException("invalid orientation")
        }
        mOrientation = orientation
    }

    fun setStartItemPosition(startItemPosition: Int): MyDefaultRecycleItemDecoration {
        this.mStartItemPosition = startItemPosition
        return this
    }

    override fun onDraw(c: Canvas, parent: RecyclerView) {
        if (mOrientation == ITEM_VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount

        var i = mStartItemPosition
        while (i < childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child))
            if (mMarginPX > 0) {
                val rectF = RectF(
                    (left + mMarginPX).toFloat(),
                    top.toFloat(),
                    (right - mMarginPX).toFloat(),
                    (top + mDecorationHeightPX).toFloat()
                )
                c.drawRect(rectF, mDecorationPaint)
            } else {
                val rectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), (top + mDecorationHeightPX).toFloat())
                c.drawRect(rectF, mDecorationPaint)
            }
            i++
        }
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount

        for (i in mStartItemPosition until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin + Math.round(ViewCompat.getTranslationY(child))
            val right = left + mDecorationHeightPX
            if (mMarginPX > 0) {
                val rectF =
                    RectF(left.toFloat(), (top + mMarginPX).toFloat(), right.toFloat(), (bottom - mMarginPX).toFloat())
                c.drawRect(rectF, mDecorationPaint)
            } else {
                val rectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
                c.drawRect(rectF, mDecorationPaint)
            }

        }
    }

    /**
     * 由于Divider也有宽高，每一个Item需要向下或者向右偏移
     * @param outRect
     * @param itemPosition
     * @param parent
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val childAdapterPosition = parent.getChildAdapterPosition(view) + 1
        if (childAdapterPosition > mStartItemPosition) {
            if (mOrientation == ITEM_VERTICAL) {
                outRect.set(0, 0, 0, mDecorationHeightPX)
            } else {
                outRect.set(0, 0, mDecorationHeightPX, 0)
            }
        }
    }

}
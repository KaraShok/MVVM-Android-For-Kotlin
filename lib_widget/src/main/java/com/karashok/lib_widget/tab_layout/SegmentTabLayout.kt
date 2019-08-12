package com.karashok.lib_widget.tab_layout

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import com.karashok.lib_widget.R
import com.karashok.lib_widget.tab_layout.listener.OnTabSelectListener
import com.karashok.lib_widget.tab_layout.utils.FragmentChangeManager
import com.karashok.lib_widget.tab_layout.utils.UnreadMsgUtils
import com.karashok.lib_widget.tab_layout.widget.MsgView

import java.util.ArrayList

class SegmentTabLayout @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(mContext, attrs, defStyleAttr), ValueAnimator.AnimatorUpdateListener {
    private var mTitles: Array<String>? = null
    private val mTabsContainer: LinearLayout
    private var mCurrentTab: Int = 0
    private var mLastTab: Int = 0
    var tabCount: Int = 0
        private set
    /** 用于绘制显示器  */
    private val mIndicatorRect = Rect()
    private val mIndicatorDrawable = GradientDrawable()
    private val mRectDrawable = GradientDrawable()

    private val mDividerPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mTabPadding: Float = 0.toFloat()
    private var mTabSpaceEqual: Boolean = false
    private var mTabWidth: Float = 0.toFloat()

    /** indicator  */
    private var mIndicatorColor: Int = 0
    private var mIndicatorHeight: Float = 0.toFloat()
    private var mIndicatorCornerRadius: Float = 0.toFloat()
    var indicatorMarginLeft: Float = 0.toFloat()
        private set
    var indicatorMarginTop: Float = 0.toFloat()
        private set
    var indicatorMarginRight: Float = 0.toFloat()
        private set
    var indicatorMarginBottom: Float = 0.toFloat()
        private set
    var indicatorAnimDuration: Long = 0
    var isIndicatorAnimEnable: Boolean = false
    var isIndicatorBounceEnable: Boolean = false

    /** divider  */
    private var mDividerColor: Int = 0
    private var mDividerWidth: Float = 0.toFloat()
    private var mDividerPadding: Float = 0.toFloat()
    private var mTextsize: Float = 0.toFloat()
    private var mTextSelectColor: Int = 0
    private var mTextUnselectColor: Int = 0
    private var mTextBold: Int = 0
    private var mTextAllCaps: Boolean = false

    private var mBarColor: Int = 0
    private var mBarStrokeColor: Int = 0
    private var mBarStrokeWidth: Float = 0.toFloat()

    private var mHeight: Int = 0

    /** anim  */
    private val mValueAnimator: ValueAnimator
    private val mInterpolator = OvershootInterpolator(0.8f)

    private var mFragmentChangeManager: FragmentChangeManager? = null
    private val mRadiusArr = FloatArray(8)

    private var mIsFirstDraw = true

    //setter and getter
    var currentTab: Int
        get() = mCurrentTab
        set(currentTab) {
            mLastTab = this.mCurrentTab
            this.mCurrentTab = currentTab
            updateTabSelection(currentTab)
            if (mFragmentChangeManager != null) {
                mFragmentChangeManager!!.setFragments(currentTab)
            }
            if (isIndicatorAnimEnable) {
                calcOffset()
            } else {
                invalidate()
            }
        }

    var tabPadding: Float
        get() = mTabPadding
        set(tabPadding) {
            this.mTabPadding = dp2px(tabPadding).toFloat()
            updateTabStyles()
        }

    var isTabSpaceEqual: Boolean
        get() = mTabSpaceEqual
        set(tabSpaceEqual) {
            this.mTabSpaceEqual = tabSpaceEqual
            updateTabStyles()
        }

    var tabWidth: Float
        get() = mTabWidth
        set(tabWidth) {
            this.mTabWidth = dp2px(tabWidth).toFloat()
            updateTabStyles()
        }

    var indicatorColor: Int
        get() = mIndicatorColor
        set(indicatorColor) {
            this.mIndicatorColor = indicatorColor
            invalidate()
        }

    var indicatorHeight: Float
        get() = mIndicatorHeight
        set(indicatorHeight) {
            this.mIndicatorHeight = dp2px(indicatorHeight).toFloat()
            invalidate()
        }

    var indicatorCornerRadius: Float
        get() = mIndicatorCornerRadius
        set(indicatorCornerRadius) {
            this.mIndicatorCornerRadius = dp2px(indicatorCornerRadius).toFloat()
            invalidate()
        }

    var dividerColor: Int
        get() = mDividerColor
        set(dividerColor) {
            this.mDividerColor = dividerColor
            invalidate()
        }

    var dividerWidth: Float
        get() = mDividerWidth
        set(dividerWidth) {
            this.mDividerWidth = dp2px(dividerWidth).toFloat()
            invalidate()
        }

    var dividerPadding: Float
        get() = mDividerPadding
        set(dividerPadding) {
            this.mDividerPadding = dp2px(dividerPadding).toFloat()
            invalidate()
        }

    var textsize: Float
        get() = mTextsize
        set(textsize) {
            this.mTextsize = sp2px(textsize).toFloat()
            updateTabStyles()
        }

    var textSelectColor: Int
        get() = mTextSelectColor
        set(textSelectColor) {
            this.mTextSelectColor = textSelectColor
            updateTabStyles()
        }

    var textUnselectColor: Int
        get() = mTextUnselectColor
        set(textUnselectColor) {
            this.mTextUnselectColor = textUnselectColor
            updateTabStyles()
        }

    var textBold: Int
        get() = mTextBold
        set(textBold) {
            this.mTextBold = textBold
            updateTabStyles()
        }

    var isTextAllCaps: Boolean
        get() = mTextAllCaps
        set(textAllCaps) {
            this.mTextAllCaps = textAllCaps
            updateTabStyles()
        }

    //setter and getter
    // show MsgTipView
    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mInitSetMap = SparseArray<Boolean>()

    private var mListener: OnTabSelectListener? = null

    private val mCurrentP = IndicatorPoint()
    private val mLastP = IndicatorPoint()

    init {
        setWillNotDraw(false)//重写onDraw方法,需要调用这个方法来清除flag
        clipChildren = false
        clipToPadding = false
        mTabsContainer = LinearLayout(mContext)
        addView(mTabsContainer)

        obtainAttributes(mContext, attrs)

        //get layout_height
        val height = attrs!!.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height")

        //create ViewPager
        if (height == ViewGroup.LayoutParams.MATCH_PARENT.toString() + "") {
        } else if (height == ViewGroup.LayoutParams.WRAP_CONTENT.toString() + "") {
        } else {
            val systemAttrs = intArrayOf(android.R.attr.layout_height)
            val a = mContext.obtainStyledAttributes(attrs, systemAttrs)
            mHeight = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT)
            a.recycle()
        }

        mValueAnimator = ValueAnimator.ofObject(PointEvaluator(), mLastP, mCurrentP)
        mValueAnimator.addUpdateListener(this)
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SegmentTabLayout)

        mIndicatorColor = ta.getColor(R.styleable.SegmentTabLayout_tl_indicator_color, Color.parseColor("#222831"))
        mIndicatorHeight = ta.getDimension(R.styleable.SegmentTabLayout_tl_indicator_height, -1f)
        mIndicatorCornerRadius = ta.getDimension(R.styleable.SegmentTabLayout_tl_indicator_corner_radius, -1f)
        indicatorMarginLeft =
            ta.getDimension(R.styleable.SegmentTabLayout_tl_indicator_margin_left, dp2px(0f).toFloat())
        indicatorMarginTop = ta.getDimension(R.styleable.SegmentTabLayout_tl_indicator_margin_top, 0f)
        indicatorMarginRight =
            ta.getDimension(R.styleable.SegmentTabLayout_tl_indicator_margin_right, dp2px(0f).toFloat())
        indicatorMarginBottom = ta.getDimension(R.styleable.SegmentTabLayout_tl_indicator_margin_bottom, 0f)
        isIndicatorAnimEnable = ta.getBoolean(R.styleable.SegmentTabLayout_tl_indicator_anim_enable, false)
        isIndicatorBounceEnable = ta.getBoolean(R.styleable.SegmentTabLayout_tl_indicator_bounce_enable, true)
        indicatorAnimDuration = ta.getInt(R.styleable.SegmentTabLayout_tl_indicator_anim_duration, -1).toLong()

        mDividerColor = ta.getColor(R.styleable.SegmentTabLayout_tl_divider_color, mIndicatorColor)
        mDividerWidth = ta.getDimension(R.styleable.SegmentTabLayout_tl_divider_width, dp2px(1f).toFloat())
        mDividerPadding = ta.getDimension(R.styleable.SegmentTabLayout_tl_divider_padding, 0f)

        mTextsize = ta.getDimension(R.styleable.SegmentTabLayout_tl_textsize, sp2px(13f).toFloat())
        mTextSelectColor = ta.getColor(R.styleable.SegmentTabLayout_tl_textSelectColor, Color.parseColor("#ffffff"))
        mTextUnselectColor = ta.getColor(R.styleable.SegmentTabLayout_tl_textUnselectColor, mIndicatorColor)
        mTextBold = ta.getInt(R.styleable.SegmentTabLayout_tl_textBold, TEXT_BOLD_NONE)
        mTextAllCaps = ta.getBoolean(R.styleable.SegmentTabLayout_tl_textAllCaps, false)

        mTabSpaceEqual = ta.getBoolean(R.styleable.SegmentTabLayout_tl_tab_space_equal, true)
        mTabWidth = ta.getDimension(R.styleable.SegmentTabLayout_tl_tab_width, dp2px(-1f).toFloat())
        mTabPadding = ta.getDimension(
            R.styleable.SegmentTabLayout_tl_tab_padding,
            (if (mTabSpaceEqual || mTabWidth > 0) dp2px(0f) else dp2px(10f)).toFloat()
        )

        mBarColor = ta.getColor(R.styleable.SegmentTabLayout_tl_bar_color, Color.TRANSPARENT)
        mBarStrokeColor = ta.getColor(R.styleable.SegmentTabLayout_tl_bar_stroke_color, mIndicatorColor)
        mBarStrokeWidth = ta.getDimension(R.styleable.SegmentTabLayout_tl_bar_stroke_width, dp2px(1f).toFloat())

        ta.recycle()
    }

    fun setTabData(titles: Array<String>?) {
        if (titles == null || titles.size == 0) {
            throw IllegalStateException("Titles can not be NULL or EMPTY !")
        }

        this.mTitles = titles

        notifyDataSetChanged()
    }

    /** 关联数据支持同时切换fragments  */
    fun setTabData(titles: Array<String>, fa: FragmentActivity, containerViewId: Int, fragments: ArrayList<Fragment>) {
        mFragmentChangeManager = FragmentChangeManager(fa.getSupportFragmentManager(), containerViewId, fragments)
        setTabData(titles)
    }

    /** 更新数据  */
    fun notifyDataSetChanged() {
        mTabsContainer.removeAllViews()
        this.tabCount = mTitles!!.size
        var tabView: View
        for (i in 0 until tabCount) {
            tabView = View.inflate(mContext, R.layout.layout_tab_segment, null)
            tabView.tag = i
            addTab(i, tabView)
        }

        updateTabStyles()
    }

    /** 创建并添加tab  */
    private fun addTab(position: Int, tabView: View) {
        val tv_tab_title = tabView.findViewById(R.id.tv_tab_title) as TextView
        tv_tab_title.text = mTitles!![position]

        tabView.setOnClickListener { v ->
            val position = v.tag as Int
            if (mCurrentTab != position) {
                currentTab = position
                if (mListener != null) {
                    mListener!!.onTabSelect(position)
                }
            } else {
                if (mListener != null) {
                    mListener!!.onTabReselect(position)
                }
            }
        }

        /** 每一个Tab的布局参数  */
        var lp_tab = if (mTabSpaceEqual)
            LinearLayout.LayoutParams(0, FrameLayout.LayoutParams.MATCH_PARENT, 1.0f)
        else
            LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT)
        if (mTabWidth > 0) {
            lp_tab = LinearLayout.LayoutParams(mTabWidth.toInt(), FrameLayout.LayoutParams.MATCH_PARENT)
        }
        mTabsContainer.addView(tabView, position, lp_tab)
    }

    private fun updateTabStyles() {
        for (i in 0 until tabCount) {
            val tabView = mTabsContainer.getChildAt(i)
            tabView.setPadding(mTabPadding.toInt(), 0, mTabPadding.toInt(), 0)
            val tv_tab_title = tabView.findViewById(R.id.tv_tab_title) as TextView
            tv_tab_title.setTextColor(if (i == mCurrentTab) mTextSelectColor else mTextUnselectColor)
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextsize)
            //            tv_tab_title.setPadding((int) mTabPadding, 0, (int) mTabPadding, 0);
            if (mTextAllCaps) {
                tv_tab_title.text = tv_tab_title.text.toString().toUpperCase()
            }

            if (mTextBold == TEXT_BOLD_BOTH) {
                tv_tab_title.paint.isFakeBoldText = true
            } else if (mTextBold == TEXT_BOLD_NONE) {
                tv_tab_title.paint.isFakeBoldText = false
            }
        }
    }

    private fun updateTabSelection(position: Int) {
        for (i in 0 until tabCount) {
            val tabView = mTabsContainer.getChildAt(i)
            val isSelect = i == position
            val tab_title = tabView.findViewById(R.id.tv_tab_title) as TextView
            tab_title.setTextColor(if (isSelect) mTextSelectColor else mTextUnselectColor)
            if (mTextBold == TEXT_BOLD_WHEN_SELECT) {
                tab_title.paint.isFakeBoldText = isSelect
            }
        }
    }

    private fun calcOffset() {
        val currentTabView = mTabsContainer.getChildAt(this.mCurrentTab)
        mCurrentP.left = currentTabView.left.toFloat()
        mCurrentP.right = currentTabView.right.toFloat()

        val lastTabView = mTabsContainer.getChildAt(this.mLastTab)
        mLastP.left = lastTabView.left.toFloat()
        mLastP.right = lastTabView.right.toFloat()

        //        Log.d("AAA", "mLastP--->" + mLastP.left + "&" + mLastP.right);
        //        Log.d("AAA", "mCurrentP--->" + mCurrentP.left + "&" + mCurrentP.right);
        if (mLastP.left == mCurrentP.left && mLastP.right == mCurrentP.right) {
            invalidate()
        } else {
            mValueAnimator.setObjectValues(mLastP, mCurrentP)
            if (isIndicatorBounceEnable) {
                mValueAnimator.interpolator = mInterpolator
            }

            if (indicatorAnimDuration < 0) {
                indicatorAnimDuration = (if (isIndicatorBounceEnable) 500 else 250).toLong()
            }
            mValueAnimator.duration = indicatorAnimDuration
            mValueAnimator.start()
        }
    }

    private fun calcIndicatorRect() {
        val currentTabView = mTabsContainer.getChildAt(this.mCurrentTab)
        val left = currentTabView.left.toFloat()
        val right = currentTabView.right.toFloat()

        mIndicatorRect.left = left.toInt()
        mIndicatorRect.right = right.toInt()

        if (!isIndicatorAnimEnable) {
            if (mCurrentTab == 0) {
                /**The corners are ordered top-left, top-right, bottom-right, bottom-left */
                mRadiusArr[0] = mIndicatorCornerRadius
                mRadiusArr[1] = mIndicatorCornerRadius
                mRadiusArr[2] = 0f
                mRadiusArr[3] = 0f
                mRadiusArr[4] = 0f
                mRadiusArr[5] = 0f
                mRadiusArr[6] = mIndicatorCornerRadius
                mRadiusArr[7] = mIndicatorCornerRadius
            } else if (mCurrentTab == tabCount - 1) {
                /**The corners are ordered top-left, top-right, bottom-right, bottom-left */
                mRadiusArr[0] = 0f
                mRadiusArr[1] = 0f
                mRadiusArr[2] = mIndicatorCornerRadius
                mRadiusArr[3] = mIndicatorCornerRadius
                mRadiusArr[4] = mIndicatorCornerRadius
                mRadiusArr[5] = mIndicatorCornerRadius
                mRadiusArr[6] = 0f
                mRadiusArr[7] = 0f
            } else {
                /**The corners are ordered top-left, top-right, bottom-right, bottom-left */
                mRadiusArr[0] = 0f
                mRadiusArr[1] = 0f
                mRadiusArr[2] = 0f
                mRadiusArr[3] = 0f
                mRadiusArr[4] = 0f
                mRadiusArr[5] = 0f
                mRadiusArr[6] = 0f
                mRadiusArr[7] = 0f
            }
        } else {
            /**The corners are ordered top-left, top-right, bottom-right, bottom-left */
            mRadiusArr[0] = mIndicatorCornerRadius
            mRadiusArr[1] = mIndicatorCornerRadius
            mRadiusArr[2] = mIndicatorCornerRadius
            mRadiusArr[3] = mIndicatorCornerRadius
            mRadiusArr[4] = mIndicatorCornerRadius
            mRadiusArr[5] = mIndicatorCornerRadius
            mRadiusArr[6] = mIndicatorCornerRadius
            mRadiusArr[7] = mIndicatorCornerRadius
        }
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        val p = animation.animatedValue as IndicatorPoint
        mIndicatorRect.left = p.left.toInt()
        mIndicatorRect.right = p.right.toInt()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isInEditMode || tabCount <= 0) {
            return
        }

        val height = height
        val paddingLeft = paddingLeft

        if (mIndicatorHeight < 0) {
            mIndicatorHeight = height.toFloat() - indicatorMarginTop - indicatorMarginBottom
        }

        if (mIndicatorCornerRadius < 0 || mIndicatorCornerRadius > mIndicatorHeight / 2) {
            mIndicatorCornerRadius = mIndicatorHeight / 2
        }

        //draw rect
        mRectDrawable.setColor(mBarColor)
        mRectDrawable.setStroke(mBarStrokeWidth.toInt(), mBarStrokeColor)
        mRectDrawable.cornerRadius = mIndicatorCornerRadius
        mRectDrawable.setBounds(getPaddingLeft(), paddingTop, width - paddingRight, getHeight() - paddingBottom)
        mRectDrawable.draw(canvas)

        // draw divider
        if (!isIndicatorAnimEnable && mDividerWidth > 0) {
            mDividerPaint.strokeWidth = mDividerWidth
            mDividerPaint.color = mDividerColor
            for (i in 0 until tabCount - 1) {
                val tab = mTabsContainer.getChildAt(i)
                canvas.drawLine(
                    (paddingLeft + tab.right).toFloat(),
                    mDividerPadding,
                    (paddingLeft + tab.right).toFloat(),
                    height - mDividerPadding,
                    mDividerPaint
                )
            }
        }


        //draw indicator line
        if (isIndicatorAnimEnable) {
            if (mIsFirstDraw) {
                mIsFirstDraw = false
                calcIndicatorRect()
            }
        } else {
            calcIndicatorRect()
        }

        mIndicatorDrawable.setColor(mIndicatorColor)
        mIndicatorDrawable.setBounds(
            paddingLeft + indicatorMarginLeft.toInt() + mIndicatorRect.left,
            indicatorMarginTop.toInt(), (paddingLeft + mIndicatorRect.right - indicatorMarginRight).toInt(),
            (indicatorMarginTop + mIndicatorHeight).toInt()
        )
        mIndicatorDrawable.cornerRadii = mRadiusArr
        mIndicatorDrawable.draw(canvas)

    }

    fun setIndicatorMargin(
        indicatorMarginLeft: Float, indicatorMarginTop: Float,
        indicatorMarginRight: Float, indicatorMarginBottom: Float
    ) {
        this.indicatorMarginLeft = dp2px(indicatorMarginLeft).toFloat()
        this.indicatorMarginTop = dp2px(indicatorMarginTop).toFloat()
        this.indicatorMarginRight = dp2px(indicatorMarginRight).toFloat()
        this.indicatorMarginBottom = dp2px(indicatorMarginBottom).toFloat()
        invalidate()
    }

    fun getTitleView(tab: Int): TextView {
        val tabView = mTabsContainer.getChildAt(tab)
        return tabView.findViewById(R.id.tv_tab_title) as TextView
    }

    /**
     * 显示未读消息
     *
     * @param position 显示tab位置
     * @param num      num小于等于0显示红点,num大于0显示数字
     */
    fun showMsg(position: Int, num: Int) {
        var position = position
        if (position >= tabCount) {
            position = tabCount - 1
        }

        val tabView = mTabsContainer.getChildAt(position)
        val tipView = tabView.findViewById(R.id.rtv_msg_tip) as MsgView
        if (tipView != null) {
            UnreadMsgUtils.show(tipView, num)

            if (mInitSetMap.get(position) != null && mInitSetMap.get(position)) {
                return
            }

            setMsgMargin(position, 2f, 2f)

            mInitSetMap.put(position, true)
        }
    }

    /**
     * 显示未读红点
     *
     * @param position 显示tab位置
     */
    fun showDot(position: Int) {
        var position = position
        if (position >= tabCount) {
            position = tabCount - 1
        }
        showMsg(position, 0)
    }

    fun hideMsg(position: Int) {
        var position = position
        if (position >= tabCount) {
            position = tabCount - 1
        }

        val tabView = mTabsContainer.getChildAt(position)
        val tipView = tabView.findViewById(R.id.rtv_msg_tip) as MsgView
        if (tipView != null) {
            tipView!!.setVisibility(View.GONE)
        }
    }

    /**
     * 设置提示红点偏移,注意
     * 1.控件为固定高度:参照点为tab内容的右上角
     * 2.控件高度不固定(WRAP_CONTENT):参照点为tab内容的右上角,此时高度已是红点的最高显示范围,所以这时bottomPadding其实就是topPadding
     */
    fun setMsgMargin(position: Int, leftPadding: Float, bottomPadding: Float) {
        var position = position
        if (position >= tabCount) {
            position = tabCount - 1
        }
        val tabView = mTabsContainer.getChildAt(position)
        val tipView = tabView.findViewById(R.id.rtv_msg_tip) as MsgView
        if (tipView != null) {
            val tv_tab_title = tabView.findViewById(R.id.tv_tab_title) as TextView
            mTextPaint.textSize = mTextsize
            val textWidth = mTextPaint.measureText(tv_tab_title.text.toString())
            val textHeight = mTextPaint.descent() - mTextPaint.ascent()
            val lp = tipView!!.getLayoutParams() as ViewGroup.MarginLayoutParams

            lp.leftMargin = dp2px(leftPadding)
            lp.topMargin =
                if (mHeight > 0) (mHeight - textHeight).toInt() / 2 - dp2px(bottomPadding) else dp2px(bottomPadding)

            tipView!!.setLayoutParams(lp)
        }
    }

    /** 当前类只提供了少许设置未读消息属性的方法,可以通过该方法获取MsgView对象从而各种设置  */
    fun getMsgView(position: Int): MsgView {
        var position = position
        if (position >= tabCount) {
            position = tabCount - 1
        }
        val tabView = mTabsContainer.getChildAt(position)
        return tabView.findViewById(R.id.rtv_msg_tip) as MsgView
    }

    fun setOnTabSelectListener(listener: OnTabSelectListener) {
        this.mListener = listener
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable("instanceState", super.onSaveInstanceState())
        bundle.putInt("mCurrentTab", mCurrentTab)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var state = state
        if (state is Bundle) {
            val bundle = state as Bundle?
            mCurrentTab = bundle!!.getInt("mCurrentTab")
            state = bundle.getParcelable("instanceState")
            if (mCurrentTab != 0 && mTabsContainer.childCount > 0) {
                updateTabSelection(mCurrentTab)
            }
        }
        super.onRestoreInstanceState(state)
    }

    internal inner class IndicatorPoint {
        var left: Float = 0.toFloat()
        var right: Float = 0.toFloat()
    }

    internal inner class PointEvaluator : TypeEvaluator<IndicatorPoint> {
        override fun evaluate(fraction: Float, startValue: IndicatorPoint, endValue: IndicatorPoint): IndicatorPoint {
            val left = startValue.left + fraction * (endValue.left - startValue.left)
            val right = startValue.right + fraction * (endValue.right - startValue.right)
            val point = IndicatorPoint()
            point.left = left
            point.right = right
            return point
        }
    }

    protected fun dp2px(dp: Float): Int {
        val scale = mContext.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    protected fun sp2px(sp: Float): Int {
        val scale = this.mContext.resources.displayMetrics.scaledDensity
        return (sp * scale + 0.5f).toInt()
    }

    companion object {

        /** title  */
        private val TEXT_BOLD_NONE = 0
        private val TEXT_BOLD_WHEN_SELECT = 1
        private val TEXT_BOLD_BOTH = 2
    }
}
